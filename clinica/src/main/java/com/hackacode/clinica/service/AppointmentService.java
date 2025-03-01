package com.hackacode.clinica.service;

import com.hackacode.clinica.config.AppConstants;
import com.hackacode.clinica.dto.*;
import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.dto.appointment.AppointmentUpdateDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ConflictException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IAppointmentMapper;
import com.hackacode.clinica.mapper.IServiceMapper;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentMapper appointmentMapper;
    private final ServiceService serviceService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final IInvoiceService invoiceService;
    private final IDoctorRepository doctorRepository;


    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toResponseDTO);
    }

    @Override
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        LocalDateTime startTime = appointmentRequestDTO.getDate().atTime(appointmentRequestDTO.getTime());
        LocalDateTime endTime = startTime.plusMinutes(AppConstants.APPOINTMENT_DURATION_MINUTES);
        var service = serviceService.getServiceEntityById(appointmentRequestDTO.getServiceId());
        var patient = patientService.getPatientById(appointmentRequestDTO.getPatientId());
        var doctor = doctorService.getDoctorById(appointmentRequestDTO.getDoctorId());

        validateAppointmentDateTime(startTime);
        validateIfDoctorProvidesService(service.getId(), doctor.getId());
        validateIfDoctorWorksThisDayAtTime(doctor.getId(), startTime, endTime);
        validateIfAppointmentIsBooked(doctor.getId(), startTime, endTime);
        validateIfPatientIsAvailable(patient, startTime, endTime);


        var invoice = invoiceService.createServiceInvoice(service, patient);
        var appointment = appointmentMapper.toEntity(appointmentRequestDTO);
        appointment.setPatient(patient);
        appointment.setService(service);
        appointment.setDoctor(doctor);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setInvoice(invoice);
        return appointmentMapper.toResponseDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to) {
        if (from.isBefore(LocalDate.now()) || to.isBefore(LocalDate.now())) {
            throw new BadRequestException("None of the dates cannot be earlier than today");
        }
        if (ChronoUnit.DAYS.between(from, to) >= 5) {
            throw new BadRequestException("The difference between fromDate and toDate cannot exceed 5 days");
        }
        List<Doctor> doctors = doctorRepository.findByServices_id(serviceId);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOS = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorAvailabilityDTOS.add(DoctorAvailabilityDTO.builder()
                    .doctorId(doctor.getId())
                    .doctorName(doctor.getUser().getName() + " " + doctor.getUser().getSurname())
                    .doctorSpeciality(doctor.getSpeciality().getName())
                    .availableSlots(getAvailableTimesForDoctor(doctor, from, to))
                    .build());
        }
        return doctorAvailabilityDTOS;
    }

    @Override
    public void deleteById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            this.getById(id);
        }
        appointmentRepository.deleteById(id);
    }


    @Override
    public AppointmentResponseDTO update(Long appoitmentId, AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = this.getById(appoitmentId);
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Cannot modify a cancelled appointment");
        }
        //TODO: Generar la factura cuando se pasa a estado COMPLETED
        appointment.setStatus(appointmentUpdateDTO.getStatus());
        if (appointmentUpdateDTO.getComments() != null) {
            appointment.setComments(appointmentUpdateDTO.getComments());
        }
        appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDTO(appointment);
    }

    private List<AvailableSlotDTO> getAvailableTimesForDoctor(Doctor doctor, LocalDate from, LocalDate to) {
        List<AvailableSlotDTO> availableDates = new ArrayList<>();
        while (from.isBefore(to) || from.equals(to)) {
            if (ifDoctorWorksThisDay(doctor, from.getDayOfWeek())) {
                List<LocalTime> bookedTimes = appointmentRepository.findBookedTimesByStatusAndDateAndDoctorId(
                        AppointmentStatus.BOOKED,
                        from,
                        doctor.getId()).stream().map(LocalDateTime::toLocalTime).toList();
                List<WorkingHour> workingHours = getWorkingHourForADay(doctor, from.getDayOfWeek());
                List<String> availableTimes = new ArrayList<>();
                for (WorkingHour workingHour : workingHours) {
                    LocalTime timeFrom = workingHour.getTimeFrom();
                    while (timeFrom.isBefore(workingHour.getTimeTo())) {
                        if (!bookedTimes.contains(timeFrom)) {
                            availableTimes.add(timeFrom.toString());
                        }
                        timeFrom = timeFrom.plusMinutes(AppConstants.APPOINTMENT_DURATION_MINUTES);
                    }
                }
                if (!availableTimes.isEmpty()) {
                    availableDates.add(
                            AvailableSlotDTO.builder()
                                    .date(from.toString())
                                    .times(availableTimes)
                                    .build()
                    );
                }
            }
            from = from.plusDays(1);
        }
        return availableDates;
    }

    private List<WorkingHour> getWorkingHourForADay(Doctor doctor, DayOfWeek dayOfWeek) {
        return doctor.getWorkingHours().stream()
                .filter(wh -> wh.getDayOfWeek().equals(dayOfWeek))
                .sorted(Comparator.comparing(WorkingHour::getTimeFrom))
                .toList();
    }

    private boolean ifDoctorWorksThisDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return doctor.getWorkingHours().stream().anyMatch(wh -> wh.getDayOfWeek().equals(dayOfWeek));
    }

    private Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with id: " + id + " not found!"));
    }

    private void validateAppointmentDateTime(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("The appointment date time cannot be before current date");
        }
    }

    private void validateIfDoctorProvidesService(Long serviceId, Long doctorId) {
        if (!doctorService.ifDoctorProvidesService(serviceId, doctorId)) {
            throw new BadRequestException("The doctor with id: " + doctorId
                    + " does not provide the service with id: " + serviceId);
        }
    }

    private void validateIfDoctorWorksThisDayAtTime(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!doctorService.ifDoctorWorksThisDayAtTime(doctorId, startTime, endTime)) {
            throw new BadRequestException("The doctor with id: " + doctorId
                    + " does not work this day at time:" + startTime);
        }
    }

    private void validateIfAppointmentIsBooked(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        if (appointmentRepository.existsBookedAppointment(doctorId, startTime, endTime)) {
            throw new ConflictException("The appointment is already booked!");
        }
    }

    private void validateIfPatientIsAvailable(Patient patient, LocalDateTime startTime, LocalDateTime endTime) {
        if (patient.getAppointments().stream().anyMatch(
                appointment -> (appointment.getStartTime().isBefore(endTime)
                        && appointment.getEndTime().isAfter(startTime) || (appointment.getStartTime().equals(startTime
                ) && appointment.getEndTime().equals(endTime))) && appointment.getStatus().equals(AppointmentStatus.BOOKED))) {
            throw new BadRequestException("The patient is not available!.");
        }
    }


}

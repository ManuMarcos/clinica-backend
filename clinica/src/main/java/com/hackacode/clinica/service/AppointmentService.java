package com.hackacode.clinica.service;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IServiceRepository serviceRepository;
    private final IAppointmentMapper appointmentMapper;
    private final ServiceService serviceService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final IDoctorRepository doctorRepository;


    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toResponseDTO);
    }

    @Override
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        return null;
    }

    @Override
    public List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to) {
        List<Doctor> doctors = doctorRepository.findByServices_id(serviceId);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOS = new ArrayList<>();
        for(Doctor doctor : doctors) {
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
        if(!appointmentRepository.existsById(id)) {
            this.getById(id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentResponseDTO createAppointmentForService(Long serviceId, AppointmentRequestDTO appointmentRequestDTO) {
        LocalDateTime startTime = appointmentRequestDTO.getDate().atTime(appointmentRequestDTO.getTime());
        var service = serviceService.findById(serviceId);
        var patient = patientService.findById(appointmentRequestDTO.getPatientId());
        var doctor = doctorService.findById(appointmentRequestDTO.getDoctorId());
        if(!doctorService.ifDoctorProvidesService(serviceId, doctor.getId())) {
            throw new BadRequestException("The doctor with id: " + doctor.getId()
                    + " does not provide the service with id: " + service.getId());
        }
        LocalDateTime endTime = startTime.plusMinutes(doctor.getAppointmentDuration());
        if(!doctorService.ifDoctorWorksThisDayAtTime(doctor.getId(),startTime, endTime)) {
            throw new BadRequestException("The doctor with id: " + doctor.getId()
                    + " does not work this day at time:" + startTime);
        }
        if(appointmentRepository.existsBookedAppointment(doctor.getId(), startTime, endTime)){
            throw new ConflictException("The appointment is already booked!");
        };
        var appointment = appointmentMapper.toEntity(appointmentRequestDTO);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setService(serviceRepository.findById(serviceId).get());
        return appointmentMapper.toResponseDTO(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDTO update(Long appoitmentId, AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = this.getById(appoitmentId);
        if(appointmentUpdateDTO.getStatus() != null){
            appointment.setStatus(appointmentUpdateDTO.getStatus());
        }
        appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDTO(appointment);
    }

    @Override
    public boolean isPatientAvailable(Patient patient, LocalDateTime startTime, LocalDateTime endTime) {
        return patient.getAppointments().stream()
                .noneMatch(appointment ->
                        appointment.getStartTime().isBefore(endTime) &&
                                appointment.getEndTime().isAfter(startTime));
    }

    private List<AvailableSlotDTO> getAvailableTimesForDoctor(Doctor doctor, LocalDate from, LocalDate to) {
        List<AvailableSlotDTO> availableDates = new ArrayList<>();
        while(from.isBefore(to) || from.equals(to)) {
            if(ifDoctorWorksThisDay(doctor, from.getDayOfWeek())){
                List<LocalTime> bookedTimes = appointmentRepository.findBookedTimesByStatusAndDateAndDoctorId(
                        AppointmentStatus.BOOKED,
                        from,
                        doctor.getId()).stream().map(LocalDateTime::toLocalTime).toList();
                List<WorkingHour> workingHours = getWorkingHourForADay(doctor, from.getDayOfWeek());
                List<AvailableTimeDTO> availableTimes = new ArrayList<>();
                for(WorkingHour workingHour : workingHours) {
                    LocalTime timeFrom = workingHour.getTimeFrom();
                    while(timeFrom.isBefore(workingHour.getTimeTo())) {
                        if(!bookedTimes.contains(timeFrom)) {
                            availableTimes.add(AvailableTimeDTO.builder()
                                    .time(timeFrom.toString())
                                    .durationInMinutes(doctor.getAppointmentDuration())
                                    .build()
                            );
                        }
                        timeFrom = timeFrom.plusMinutes(doctor.getAppointmentDuration());
                    }
                }
                if(!availableTimes.isEmpty()) {
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


    private boolean ifDoctorProvidesService(Doctor doctor, com.hackacode.clinica.model.Service service) {
        return doctor.getServices().stream().anyMatch(s -> s.getId().equals(service.getId()));
    }

    private boolean ifDoctorWorksThisDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return doctor.getWorkingHours().stream().anyMatch(wh -> wh.getDayOfWeek().equals(dayOfWeek));
    }

    private Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with id: " + id + " not found!"));
    }

    private boolean ifDoctorWorksThisDayAtTime(Doctor doctor, LocalDateTime dateTime) {
        return doctor.getWorkingHours().stream().anyMatch(
                wh -> ((wh.getDayOfWeek().equals(dateTime.getDayOfWeek())
                    && !dateTime.toLocalTime().isBefore(wh.getTimeFrom())
                    && !dateTime.toLocalTime().isAfter(wh.getTimeTo()))));
    }





}

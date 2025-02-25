package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.*;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ConflictException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.AppointmentMapper;
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
    private final IPatientRepository patientRepository;
    private final IDoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;


    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDTO);
    }

    @Override
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        LocalDateTime startTime = appointmentRequestDTO.getDate().atTime(appointmentRequestDTO.getTime());
        var service = serviceRepository.findById(appointmentRequestDTO.getServiceId()).orElseThrow(
                () -> new ResourceNotFoundException("Service with id: " + appointmentRequestDTO.getServiceId()
                        + "not found!"));
        var patient = patientRepository.findById(appointmentRequestDTO.getPatientId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id: " + appointmentRequestDTO.getPatientId()
                        + "not found!"));
        var doctor = doctorRepository.findById(appointmentRequestDTO.getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("Doctor with id: " + appointmentRequestDTO.getDoctorId()
                    + "not found!"));
        if(!ifDoctorProvidesService(doctor, service)) {
            throw new BadRequestException("The doctor with id: " + doctor.getId()
                + "does not provide the service with id: " + service.getId());
        }
        if(!ifDoctorWorksThisDayAtTime(doctor, startTime)){
            throw new BadRequestException("The doctor with id: " + doctor.getId()
                + "does not work this day at time:" + startTime);
        }
        LocalDateTime endTime = startTime.plusMinutes(doctor.getAppointmentDuration());
        if(appointmentRepository.existsBookedAppointment(doctor.getId(), startTime, endTime)){
            throw new ConflictException("The appointment is already booked!");
        };

        var savedAppointment = appointmentRepository.save(Appointment.builder()
                .service(service)
                .doctor(doctor)
                .patient(patient)
                .status(AppointmentStatus.BOOKED)
                .startTime(startTime)
                .endTime(endTime)
                .build());
        return appointmentMapper.toDTO(savedAppointment);
    }

    @Override
    public List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to) {
        List<Doctor> doctors = doctorRepository.findByServices_id(serviceId);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOS = new ArrayList<>();
        for(Doctor doctor : doctors) {
            doctorAvailabilityDTOS.add(DoctorAvailabilityDTO.builder()
                    .doctorId(doctor.getId())
                    .doctorName(doctor.getName() + " " + doctor.getSurname())
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
    public AppointmentResponseDTO update(Long appoitmentId, AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = this.getById(appoitmentId);
        if(appointmentUpdateDTO.getStatus() != null){
            appointment.setStatus(appointmentUpdateDTO.getStatus());
        }
        appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
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

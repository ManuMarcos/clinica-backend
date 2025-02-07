package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.*;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        var service = serviceRepository.findById(appointmentRequestDTO.getServiceId()).orElseThrow(
                () -> new ResourceNotFoundException("Service with id: " + appointmentRequestDTO.getServiceId()
                        + "not found!"));
        var patient = patientRepository.findById(appointmentRequestDTO.getPatientId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id: " + appointmentRequestDTO.getPatientId()
                        + "not found!"));
        var doctor = doctorRepository.findById(appointmentRequestDTO.getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("Doctor with id: " + appointmentRequestDTO.getDoctorId()
                    + "not found!"));

        LocalDateTime startTime = appointmentRequestDTO.getDate().atTime(appointmentRequestDTO.getTime());
        LocalDateTime endTime = startTime.plusMinutes(doctor.getAppointmentDuration());
        //var availableDoctors = doctorService.findAvailableDoctors(startTime, endTime, service.getId());
        //var randomDoctor = availableDoctors.get(new Random().nextInt(availableDoctors.size()));

        //Create de Appointment
        var appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .service(service)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return null;
    }

    @Override
    public List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to) {
        List<Doctor> doctors = doctorRepository.findByServices_id(serviceId);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOS = new ArrayList<>();
        for(Doctor doctor : doctors) {
            doctorAvailabilityDTOS.add(DoctorAvailabilityDTO.builder()
                    .doctorId(doctor.getId())
                    .doctorName(doctor.getName() + " " + doctor.getSurname())
                    .availableSlots(getAvailableTimesForDoctor(doctor, from, to))
                    .build());
        }
        return doctorAvailabilityDTOS;
    }

    @Override
    public AppointmentResponseDTO update(AppointmentRequestDTO appointmentRequestDTO) {
        return null;
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

    private boolean ifDoctorWorksThisDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return doctor.getWorkingHours().stream().anyMatch(wh -> wh.getDayOfWeek().equals(dayOfWeek));
    }



}

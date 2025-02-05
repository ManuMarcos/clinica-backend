package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.AppointmentRequestDTO;
import com.hackacode.clinica.dto.AppointmentResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IServiceRepository serviceRepository;
    private final IPatientRepository patientRepository;
    private final IDoctorService doctorService;


    @Override
    public AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO) {
        /*
        var service = serviceRepository.findById(appointmentRequestDTO.getServiceId()).orElseThrow(
                () -> new ResourceNotFoundException("Service with id: " + appointmentRequestDTO.getServiceId()
                        + "not found!")
        );
        var patient = patientRepository.findById(appointmentRequestDTO.getPatientId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id: " + appointmentRequestDTO.getPatientId()
                        + "not found!")
        );
        LocalDateTime startTime = appointmentRequestDTO.getDateTime();
        LocalDateTime endTime = appointmentRequestDTO.getDateTime().plusMinutes(service.getDuration());
        var availableDoctors = doctorService.findAvailableDoctors(startTime, endTime, service.getId());
        var randomDoctor = availableDoctors.get(new Random().nextInt(availableDoctors.size()));

        //Create de Appointment
        var appointment = Appointment.builder()
                .doctor(randomDoctor)
                .patient(patient)
                .service(service)
                .startTime(startTime)
                .endTime(endTime)
                .build();

         */
        return null;
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


}

package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.AppointmentResponseDTO;
import com.hackacode.clinica.dto.DoctorBasicDTO;
import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final ServiceMapper serviceMapper;

    public AppointmentResponseDTO toDTO(Appointment appointment) {
        DoctorDTO doctor = doctorMapper.toDTO(appointment.getDoctor());
        return AppointmentResponseDTO.builder()
                .id(appointment.getAppointmentId())
                .dateTime(appointment.getStartTime())
                .patient(patientMapper.toDTO(appointment.getPatient()))
                .service(serviceMapper.toDTO(appointment.getService()))
                .doctor(DoctorBasicDTO.builder()
                        .name(doctor.getName())
                        .surname(doctor.getSurname())
                        .speciality(doctor.getSpeciality())
                        .build())
                .status(appointment.getStatus())
                .build();

    }


}

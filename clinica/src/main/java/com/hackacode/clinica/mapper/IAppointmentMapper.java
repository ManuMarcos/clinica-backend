package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {IPatientMapper.class, IServiceMapper.class,
IDoctorMapper.class})
public interface IAppointmentMapper {

    @Mapping(target = "dateTime", source = "startTime")
    @Mapping(target = "patientName", source = "patient.user.name")
    @Mapping(target = "doctorName", source = "doctor.user.name")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "serviceCode", source = "service.serviceCode")
    AppointmentResponseDTO toResponseDTO(Appointment appointment);

    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "patient.id", source = "patientId")
    Appointment toEntity(AppointmentRequestDTO appointmentRequestDTO);
}

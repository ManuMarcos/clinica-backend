package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.model.Appointment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {IPatientMapper.class, IServiceMapper.class})
public interface IAppointmentMapper {

    AppointmentResponseDTO toResponseDTO(Appointment appointment);
    
    Appointment toEntity(AppointmentRequestDTO appointmentRequestDTO);
}

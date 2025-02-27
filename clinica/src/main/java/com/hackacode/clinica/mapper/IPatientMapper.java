package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.patient.PatientRequestDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.model.Patient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {IUserMapper.class, IHealthInsuranceMapper.class,
        IAppointmentMapper.class})
public interface IPatientMapper {
    PatientResponseDTO toResponseDTO(Patient patient);
    Patient toEntity(PatientRequestDTO patientRequestDTO);
}

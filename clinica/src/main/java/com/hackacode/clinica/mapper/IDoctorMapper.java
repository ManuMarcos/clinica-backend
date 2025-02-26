package com.hackacode.clinica.mapper;


import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.model.Doctor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface IDoctorMapper {

    DoctorResponseDTO toResponseDTO(Doctor doctor);
    Doctor toEntity(DoctorRequestDTO doctorRequestDTO);
}

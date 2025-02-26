package com.hackacode.clinica.mapper;


import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Speciality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ISpecialityMapper.class, IServiceMapper.class,
IAppointmentMapper.class, IWorkingHourMapper.class, IUserMapper.class})
public interface IDoctorMapper {
    DoctorResponseDTO toResponseDTO(Doctor doctor);

    Doctor toEntity(DoctorRequestDTO doctorRequestDTO);
}

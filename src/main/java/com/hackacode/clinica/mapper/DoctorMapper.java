package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.ServiceDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Speciality;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final ServiceMapper serviceMapper;

    public DoctorDTO toDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .dni(doctor.getDni())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .email(doctor.getEmail())
                .salary(doctor.getSalary())
                .role(doctor.getRole())
                .speciality(doctor.getSpeciality() != null ? doctor.getSpeciality().getName() : null)
                .birthDate(doctor.getBirthDate())
                //TODO: Cambiar el WorkingHourDTO estatico por un mapper
                .workingHours(doctor.getWorkingHours() == null ? null :
                        doctor.getWorkingHours().stream().map(WorkingHourDTO::from).toList())
                .services(doctor.getServices() == null ? null :
                        doctor.getServices().stream().map(serviceMapper::toDTO).toList())
                .build();
    }

    public Doctor toEntity(DoctorDTO doctorDTO, Speciality speciality) {
        return Doctor.builder()
                .name(doctorDTO.getName())
                .surname(doctorDTO.getSurname())
                .dni(doctorDTO.getDni())
                .email(doctorDTO.getEmail())
                .password(doctorDTO.getPassword())
                .birthDate(doctorDTO.getBirthDate())
                .speciality(speciality)
                .salary(doctorDTO.getSalary())
                .build();
    }
}

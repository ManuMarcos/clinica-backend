package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Speciality;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDTO toDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .dni(doctor.getDni())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .email(doctor.getEmail())
                .salary(doctor.getSalary())
                .speciality(doctor.getSpeciality().getName())
                .birthDate(doctor.getBirthDate())
                .workingHours(doctor.getWorkingHours().stream().map(
                        WorkingHourDTO::from
                ).toList())
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
                .salary(doctorDTO.getSalary())
                .build();
    }
}

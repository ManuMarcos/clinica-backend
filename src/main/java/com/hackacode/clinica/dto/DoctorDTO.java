package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DoctorDTO {
    private Long id;
    private String dni;
    private String name;
    private String surname;
    private String email;
    private Double salary;
    private String speciality;
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    private List<WorkingHourDTO> workingHours;

    public static DoctorDTO from(Doctor doctor) {
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


}

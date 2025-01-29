package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DoctorResponseDTO {
    private Long id;
    private String dni;
    private String name;
    private String surname;
    private String email;
    private Double salary;
    private String speciality;
    private LocalDate dateOfBirth;
    private Role role;
}

package com.hackacode.clinica.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoctorRequestDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String dni;
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    private BigDecimal salary;
    @JsonProperty(value ="speciality_id")
    private Long specialityId;
}

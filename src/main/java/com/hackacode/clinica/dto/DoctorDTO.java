package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.util.Views;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String dni;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private Role role;

    private BigDecimal salary;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String speciality;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<WorkingHourDTO> workingHours;

    @JsonProperty(value ="speciality_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long specialityId;

}

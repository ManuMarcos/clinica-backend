package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.HealthInsurance;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO{

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    @JsonProperty("health_insurance")
    private HealthInsuranceDTO healthInsurance;

}

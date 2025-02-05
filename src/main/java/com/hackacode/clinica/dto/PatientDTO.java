package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.HealthInsurance;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Optional;

@SuperBuilder
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO extends UserDTO{

    @JsonProperty("health_insurance")
    private HealthInsuranceDTO healthInsurance;

}

package com.hackacode.clinica.dto.authentication;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceRequestDTO;
import com.hackacode.clinica.model.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO{

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The surname cannot be null")
    @NotBlank(message = "The surname cannot be empty")
    private String surname;

    @NotNull(message = "The email cannot be null")
    @NotBlank(message = "The email cannot be empty")
    private String email;

    @NotNull(message = "The dni cannot be null")
    @NotBlank(message = "The dni cannot be empty")
    private String dni;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The birth date cannot be null")
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @NotNull(message = "The role cannot be null")
    private Role role;

    @NotNull(message = "The password cannot be null")
    @NotBlank(message = "The password cannot be empty")
    //TODO: Falta validar la complejidad de la contrasena
    private String password;

    private Double salary;


    @JsonProperty("speciality_id")
    private Long specialityId;

    @Valid
    @JsonProperty("health_insurance")
    private HealthInsuranceRequestDTO healthInsurance;
}

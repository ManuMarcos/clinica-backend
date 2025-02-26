package com.hackacode.clinica.dto.healthInsurance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HealthInsuranceRequestDTO {
    @NotNull(message = "The name of the Health Insurance cannot be null")
    @NotBlank(message = "The name of the Health Insurance cannot be empty")
    private String name;

    @NotNull(message = "The plan of the Health Insurance cannot be null")
    @NotBlank(message = "The plan of the Health Insurance cannot be empty")
    private String plan;

    @NotNull(message = "The number of the Health Insurance cannot be null")
    @NotBlank(message = "The number of the Health Insurance cannot be empty")
    private String number;
}

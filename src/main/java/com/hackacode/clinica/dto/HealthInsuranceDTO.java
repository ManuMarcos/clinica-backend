package com.hackacode.clinica.dto;

import com.hackacode.clinica.model.HealthInsurance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class HealthInsuranceDTO {

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

package com.hackacode.clinica.dto.healthInsurance;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HealthInsuranceResponseDTO {
    private String name;
    private String plan;
    private String number;
}

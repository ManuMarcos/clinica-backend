package com.hackacode.clinica.dto.healthInsurance;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HealthInsuranceResponseDTO {
    private String name;
    private String plan;
    private String number;
}

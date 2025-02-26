package com.hackacode.clinica.dto.speciality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpecialityResponseDTO {
    private Long id;
    private String name;
}

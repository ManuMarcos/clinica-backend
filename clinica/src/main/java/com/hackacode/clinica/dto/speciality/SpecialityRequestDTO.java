package com.hackacode.clinica.dto.speciality;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SpecialityRequestDTO {

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;
}

package com.hackacode.clinica.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String dni;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private Role role;
}

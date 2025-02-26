package com.hackacode.clinica.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserRequestDTO {
    private String name;

    private String surname;

    private String email;

    private String password;

    private String dni;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private Role role;

}

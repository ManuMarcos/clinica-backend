package com.hackacode.clinica.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String dni;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private Role role;

}

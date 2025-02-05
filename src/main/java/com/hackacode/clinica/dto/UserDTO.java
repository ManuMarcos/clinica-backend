package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

}

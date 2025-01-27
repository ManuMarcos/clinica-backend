package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The surname cannot be null")
    @NotBlank(message = "The surname cannot be empty")
    private String surname;

    @NotNull(message = "The email cannot be null")
    @NotBlank(message = "The email cannot be empty")
    private String email;

    @NotNull(message = "The dni cannot be null")
    @NotBlank(message = "The dni cannot be empty")
    private String dni;

    @JsonProperty("date_of_birth")
    @NotNull(message = "The date of birth cannot be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "The role cannot be null")
    private Role role;
}

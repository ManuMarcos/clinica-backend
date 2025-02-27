package com.hackacode.clinica.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private String position;
}

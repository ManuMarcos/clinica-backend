package com.hackacode.clinica.dto.admin;

import com.hackacode.clinica.dto.user.UserRequestDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponseDTO {
    private Long id;
    private UserRequestDTO user;
    private String position;
}

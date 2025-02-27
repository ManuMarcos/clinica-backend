package com.hackacode.clinica.dto.admin;


import com.hackacode.clinica.dto.user.UserRequestDTO;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDTO {
    private UserRequestDTO user;
    private String position;
}

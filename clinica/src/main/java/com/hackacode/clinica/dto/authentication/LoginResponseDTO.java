package com.hackacode.clinica.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDTO {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        private UserResponseDTO user;
}


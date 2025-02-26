package com.hackacode.clinica.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AccessTokenDTO {
    @JsonProperty("access_token")
    private String accessToken;

    private UserResponseDTO user;
}

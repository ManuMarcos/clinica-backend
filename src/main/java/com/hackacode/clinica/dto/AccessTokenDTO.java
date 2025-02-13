package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AccessTokenDTO {
    @JsonProperty("access_token")
    private String accessToken;
}

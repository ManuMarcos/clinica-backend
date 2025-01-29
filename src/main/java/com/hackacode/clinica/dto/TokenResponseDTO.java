package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDTO(
        @JsonProperty("accessToken")
        String accessToken
) {
}

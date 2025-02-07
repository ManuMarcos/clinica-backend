package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class AvailableTimeDTO {

    private String time;

    @JsonProperty("duration_in_minutes")
    private int durationInMinutes;
}

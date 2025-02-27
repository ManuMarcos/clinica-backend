package com.hackacode.clinica.dto.workingHour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class WorkingHourRequestDTO {

    @JsonProperty("day_of_week")
    @NotNull(message = "day_of_week cannot be null")
    private DayOfWeek dayOfWeek;

    @JsonProperty("time_from")
    @NotNull(message = "time_from cannot be null")
    private LocalTime timeFrom;

    @JsonProperty("time_to")
    @NotNull(message = "time_to cannot be null")
    private LocalTime timeTo;
}

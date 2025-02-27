package com.hackacode.clinica.dto.workingHour;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class WorkingHourResponseDTO {

    private Long id;
    @JsonProperty("day_of_week")
    private DayOfWeek dayOfWeek;
    @JsonProperty("time_from")
    private LocalTime timeFrom;
    @JsonProperty("time_to")
    private LocalTime timeTo;
}

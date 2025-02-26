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
    private DayOfWeek dayOfWeek;
    private LocalTime timeFrom;
    private LocalTime timeTo;
}

package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.WorkingHour;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
@Getter
@Setter
public class WorkingHourDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty("day_of_week")
    @NotNull(message = "day_of_week cannot be null")
    private DayOfWeek dayOfWeek;
    @JsonProperty("time_from")
    @NotNull(message = "time_from cannot be null")
    private LocalTime timeFrom;
    @JsonProperty("time_to")
    @NotNull(message = "time_to cannot be null")
    private LocalTime timeTo;

    public static WorkingHour toEntity(WorkingHourDTO dto) {
        return WorkingHour.builder()
                .workingHourId(dto.getId())
                .dayOfWeek(dto.dayOfWeek)
                .timeFrom(dto.getTimeFrom())
                .timeTo(dto.getTimeTo())
                .build();
    }

    public static WorkingHourDTO from(WorkingHour entity) {
        return WorkingHourDTO.builder()
                .id(entity.getWorkingHourId())
                .dayOfWeek(entity.getDayOfWeek())
                .timeFrom(entity.getTimeFrom())
                .timeTo(entity.getTimeTo())
                .build();
    }



}

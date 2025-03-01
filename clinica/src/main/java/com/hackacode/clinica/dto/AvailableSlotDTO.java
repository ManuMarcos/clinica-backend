package com.hackacode.clinica.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AvailableSlotDTO {

    private String date;

    private List<String> times;
}

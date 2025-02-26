package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.workingHour.WorkingHourRequestDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourResponseDTO;
import com.hackacode.clinica.model.WorkingHour;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IWorkingHourMapper {

    WorkingHourResponseDTO toResponseDTO(WorkingHour workingHour);

    WorkingHour toEntity(WorkingHourRequestDTO workingHourRequestDTO);
}

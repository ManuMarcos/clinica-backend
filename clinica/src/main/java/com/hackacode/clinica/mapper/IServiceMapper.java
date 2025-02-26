package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.service.ServiceRequestDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.model.Service;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IServiceMapper {

    ServiceResponseDTO toResponseDTO(Service service);
    Service toEntity(ServiceRequestDTO serviceRequestDTO);
}

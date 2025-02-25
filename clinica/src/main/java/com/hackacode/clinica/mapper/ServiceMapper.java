package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.ServiceDTO;
import com.hackacode.clinica.model.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceDTO toDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .serviceCode(service.getServiceCode())
                .price(service.getPrice())
                .description(service.getDescription())
                .build();
    }

    public Service toEntity(ServiceDTO serviceDTO) {
        return Service.builder()
                .serviceCode(serviceDTO.getServiceCode())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .name(serviceDTO.getName())
                .build();
    }
}

package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.service.ServiceRequestDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServiceService {

    ServiceResponseDTO save(ServiceRequestDTO serviceDTO);
    List<ServiceResponseDTO> findAll(Pageable pageable);
    ServiceResponseDTO findById(Long id);
    void delete(Long id);
    boolean existsById(Long id);
}

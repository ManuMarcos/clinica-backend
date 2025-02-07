package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorAvailabilityDTO;
import com.hackacode.clinica.dto.ServiceDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServiceService {

    ServiceDTO save(ServiceDTO serviceDTO);
    List<ServiceDTO> findAll(Pageable pageable);
    ServiceDTO findById(Long id);
    void delete(Long id);
    boolean existsById(Long id);
}

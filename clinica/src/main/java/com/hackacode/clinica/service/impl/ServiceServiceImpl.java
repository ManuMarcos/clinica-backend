package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.dto.service.ServiceRequestDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IServiceMapper;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Service;
import com.hackacode.clinica.repository.IPackageRepository;
import com.hackacode.clinica.repository.IServiceRepository;
import com.hackacode.clinica.service.IServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements IServiceService {

    private final IServiceRepository serviceRepository;
    private final IPackageRepository packageRepository;
    private final IServiceMapper serviceMapper;

    @Override
    public ServiceResponseDTO save(ServiceRequestDTO serviceRequestDTO) {
        this.validateService(serviceRequestDTO);
        var individualService = serviceMapper.toEntity(serviceRequestDTO);
        return serviceMapper.toResponseDTO(serviceRepository.save(individualService));
    }

    @Override
    public List<ServiceResponseDTO> findAll(Pageable pageable) {
        var services =  serviceRepository.findAll(pageable);
        return services.stream().map(serviceMapper::toResponseDTO).toList();
    }

    @Override
    public ServiceResponseDTO findById(Long id) {
        var service = this.getServiceEntityById(id);
        return serviceMapper.toResponseDTO(service);
    }

    @Override
    public void delete(Long id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("Service with id " + id + " not found")
        );
        // Recorro todos los paquetes que contienen el servicio
        List<com.hackacode.clinica.model.Package> packages = packageRepository.findByServicesContaining(service);
        for (Package pkg : packages) {
            pkg.getServices().remove(service);
            packageRepository.save(pkg);  // Guardo el update
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return serviceRepository.existsById(id);
    }

    @Override
    public Service getServiceEntityById(Long id) {
        return serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with id: " + id)
        );
    }


    private void validateService(ServiceRequestDTO dto){
        if(serviceRepository.existsByServiceCode(dto.getServiceCode())){
            throw new IllegalArgumentException("Service code is already in use");
        }
    }


}

package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.ServiceDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Service;
import com.hackacode.clinica.repository.IPackageRepository;
import com.hackacode.clinica.repository.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService implements IServiceService {

    private final IServiceRepository serviceRepository;
    private final IPackageRepository packageRepository;

    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {
        this.validateService(serviceDTO);
        var individualService = ServiceDTO.toEntity(serviceDTO);
        var savedIndividualService = this.serviceRepository.save(individualService);
        return ServiceDTO.from(savedIndividualService);
    }

    @Override
    public List<ServiceDTO> findAll(Pageable pageable) {
        var services =  serviceRepository.findAll(pageable);
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        for (Service service : services) {
            serviceDTOS.add(ServiceDTO.from(service));
        }
        return serviceDTOS;
    }

    @Override
    public ServiceDTO findById(Long id) {
        var service = serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with id: " + id)
        );
        return ServiceDTO.from(service);
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


    private void validateService(ServiceDTO dto){
        if(serviceRepository.existsByServiceCode(dto.getServiceCode())){
            throw new IllegalArgumentException("Service code is already in use");
        }
    }


}

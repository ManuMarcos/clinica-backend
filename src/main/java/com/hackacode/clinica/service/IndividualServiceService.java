package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.IndividualServiceDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.IndividualService;
import com.hackacode.clinica.repository.IIndividualServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualServiceService implements IIndividualServiceService {

    private final IIndividualServiceRepository individualServiceRepository;

    @Override
    public IndividualServiceDTO save(IndividualServiceDTO individualServiceDTO) {
        this.validateService(individualServiceDTO);
        var individualService = this.dtoToEntity(individualServiceDTO);
        var savedIndividualService = this.individualServiceRepository.save(individualService);
        return entityToDto(savedIndividualService);
    }

    @Override
    public List<IndividualServiceDTO> findAll(Pageable pageable) {
        var individualServices =  individualServiceRepository.findAll(pageable);
        List<IndividualServiceDTO> individualServiceDTOS = new ArrayList<>();
        for (IndividualService individualService : individualServices) {
            individualServiceDTOS.add(entityToDto(individualService));
        }
        return individualServiceDTOS;
    }

    @Override
    public IndividualServiceDTO findById(Long id) {
        var individualService = individualServiceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with id: " + id)
        );
        return entityToDto(individualService);
    }

    @Override
    public void delete(Long id) {
        if(!individualServiceRepository.existsById(id)){
            throw new ResourceNotFoundException("Service with id " + id + " not found");
        }
        individualServiceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return individualServiceRepository.existsById(id);
    }

    @Override
    public IndividualServiceDTO entityToDto(IndividualService entity){
        return IndividualServiceDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .serviceCode(entity.getServiceCode())
                .price(entity.getPrice())
                .build();
    }

    private IndividualService dtoToEntity(IndividualServiceDTO dto){
        return IndividualService.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .serviceCode(dto.getServiceCode())
                .description(dto.getDescription())
                .build();
    }

    private void validateService(IndividualServiceDTO dto){
        if(individualServiceRepository.existsByServiceCode(dto.getServiceCode())){
            throw new IllegalArgumentException("Service code is already in use");
        }
    }


}

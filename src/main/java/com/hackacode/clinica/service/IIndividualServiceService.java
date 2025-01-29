package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.IndividualServiceDTO;
import com.hackacode.clinica.model.IndividualService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IIndividualServiceService {

    IndividualServiceDTO save(IndividualServiceDTO individualServiceDTO);
    List<IndividualServiceDTO> findAll(Pageable pageable);
    IndividualServiceDTO findById(Long id);
    void delete(Long id);
    IndividualServiceDTO entityToDto(IndividualService entity);
    boolean existsById(Long id);
}

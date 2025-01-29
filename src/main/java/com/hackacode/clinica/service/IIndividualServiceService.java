package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.IndividualServiceDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IIndividualServiceService {

    IndividualServiceDTO save(IndividualServiceDTO individualServiceDTO);
    List<IndividualServiceDTO> findAll(Pageable pageable);
    void delete(Long id);

}

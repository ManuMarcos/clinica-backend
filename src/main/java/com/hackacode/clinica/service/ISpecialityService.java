package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;

import java.util.List;

public interface ISpecialityService {

    SpecialityResponseDTO save(SpecialityRequestDTO specialityRequestDTO);
    List<SpecialityResponseDTO> findAll();
    SpecialityResponseDTO update(Long id, SpecialityRequestDTO specialityRequestDTO);
    void delete(Long id);
    SpecialityResponseDTO findById(Long id);
}

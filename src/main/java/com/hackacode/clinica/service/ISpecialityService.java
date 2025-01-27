package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;

import java.util.List;

public interface ISpecialityService {

    SpecialityResponseDTO saveSpeciality(SpecialityRequestDTO specialityRequestDTO);
    List<SpecialityResponseDTO> findAll();
}

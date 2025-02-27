package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.speciality.SpecialityRequestDTO;
import com.hackacode.clinica.dto.speciality.SpecialityResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISpecialityService {

    SpecialityResponseDTO save(SpecialityRequestDTO specialityRequestDTO);
    Page<SpecialityResponseDTO> findAll(Pageable pageable);
    SpecialityResponseDTO update(Long id, SpecialityRequestDTO specialityRequestDTO);
    void delete(Long id);
    SpecialityResponseDTO findById(Long id);

}

package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;
import com.hackacode.clinica.model.Speciality;
import com.hackacode.clinica.repository.ISpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialityService implements ISpecialityService {

    private final ISpecialityRepository specialityRepository;

    @Override
    public SpecialityResponseDTO saveSpeciality(SpecialityRequestDTO specialityRequestDTO) {
        Speciality speciality = specialityRepository.save(Speciality.builder()
                .name(specialityRequestDTO.getName())
                .build());
        return SpecialityResponseDTO.builder()
                .id(speciality.getSpecialityId())
                .name(speciality.getName())
                .build();
    }

    @Override
    public List<SpecialityResponseDTO> findAll() {
        var specialities = specialityRepository.findAll();
        var specialitiesDTO = new ArrayList<SpecialityResponseDTO>();
        for (Speciality speciality : specialities) {
            specialitiesDTO.add(SpecialityResponseDTO.builder()
                    .id(speciality.getSpecialityId())
                    .name(speciality.getName())
                    .build());
        }
        return specialitiesDTO;
    }
}

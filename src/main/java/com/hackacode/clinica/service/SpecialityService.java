package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
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
    public SpecialityResponseDTO save(SpecialityRequestDTO specialityRequestDTO) {
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

    @Override
    public SpecialityResponseDTO update(Long id, SpecialityRequestDTO specialityRequestDTO) {
        var speciality = this.getById(id);
        speciality.setName(specialityRequestDTO.getName());
        var updatedSpeciality = specialityRepository.save(speciality);
        return SpecialityResponseDTO.builder()
                .id(updatedSpeciality.getSpecialityId())
                .name(updatedSpeciality.getName())
                .build();
    }


    @Override
    public void delete(Long id) {
        var speciality = this.getById(id);
        specialityRepository.deleteById(id);
    }

    @Override
    public SpecialityResponseDTO findById(Long id) {
        var speciality = this.getById(id);
        return SpecialityResponseDTO.builder()
                .name(speciality.getName())
                .id(speciality.getSpecialityId())
                .build();
    }

    private Speciality getById(Long id) {
        return specialityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Speciality with ID " + id + " does not exist"));
    }

}

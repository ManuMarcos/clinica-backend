package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.dto.speciality.SpecialityRequestDTO;
import com.hackacode.clinica.dto.speciality.SpecialityResponseDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.ISpecialityMapper;
import com.hackacode.clinica.model.Speciality;
import com.hackacode.clinica.repository.IDoctorRepository;
import com.hackacode.clinica.repository.ISpecialityRepository;
import com.hackacode.clinica.service.ISpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements ISpecialityService {

    private final ISpecialityRepository specialityRepository;
    private final IDoctorRepository doctorRepository;
    private final ISpecialityMapper specialityMapper;

    @Override
    public SpecialityResponseDTO save(SpecialityRequestDTO specialityRequestDTO) {
        if(specialityRepository.existsByName(specialityRequestDTO.getName())){
            throw new BadRequestException("The speciality already exists");
        }
        Speciality speciality = specialityRepository.save(specialityMapper.toEntity(specialityRequestDTO));
        return specialityMapper.toResponseDTO(speciality);
    }

    @Override
    public Page<SpecialityResponseDTO> findAll(Pageable pageable) {
        var specialities = specialityRepository.findAll();
        var specialitiesDTO = specialities.stream().map(specialityMapper::toResponseDTO).toList();
        return new PageImpl<>(specialitiesDTO, pageable, specialitiesDTO.size());
    }

    @Override
    public SpecialityResponseDTO update(Long id, SpecialityRequestDTO specialityRequestDTO) {
        var speciality = this.getById(id);
        speciality.setName(specialityRequestDTO.getName());
        return specialityMapper.toResponseDTO(specialityRepository.save(speciality));
    }


    @Override
    public void delete(Long id) {
        if(doctorRepository.existsBySpecialityId(id)){
            throw new BadRequestException("No se puede eliminar la especialidad porque existen doctores que " +
                    "pertenecen a la misma");
        }
        specialityRepository.deleteById(id);
    }

    @Override
    public SpecialityResponseDTO findById(Long id) {
        var speciality = this.getById(id);
        return specialityMapper.toResponseDTO(speciality);
    }

    private Speciality getById(Long id) {
        return specialityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Speciality with ID " + id + " does not exist"));
    }

}

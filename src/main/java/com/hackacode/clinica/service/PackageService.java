package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.IndividualServiceDTO;
import com.hackacode.clinica.dto.PackageDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.IndividualService;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.repository.IIndividualServiceRepository;
import com.hackacode.clinica.repository.IPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService implements IPackageService {

    private final IPackageRepository packageRepository;
    private final IIndividualServiceService individualService;
    private final IIndividualServiceRepository individualServiceRepository;

    @Override
    public PackageDTO save(PackageDTO packageDTO) {
        this.validatePackageCode(packageDTO.getPackageCode());
        Package packageEntity = dtoToEntity(packageDTO);
        for(Integer id : packageDTO.getIndividualServicesIds()){
            IndividualService individualService = individualServiceRepository.findById(Integer.toUnsignedLong(id))
                    .orElseThrow(() -> new IllegalArgumentException("Individual service with id " + id + " not found"));
            packageEntity.addIndividualService(individualService);
        }
        var savedPackageEntity = packageRepository.save(packageEntity);
        return entityToDto(savedPackageEntity);
    }

    @Override
    public List<PackageDTO> findAll(Pageable pageable) {
        var packages = packageRepository.findAll(pageable);
        List<PackageDTO> packageDTOS = new ArrayList<>();

        for(Package pack : packages){
            packageDTOS.add(entityToDto(pack));
        }
        return packageDTOS;
    }

    @Override
    public void deleteById(Long id) {
        Package pack = this.getById(id);
        packageRepository.delete(pack);
    }

    @Override
    public PackageDTO findById(Long id) {
        return entityToDto(this.getById(id));
    }

    private Package dtoToEntity(PackageDTO dto){
        Package entity = new Package();
        entity.setPackageCode(dto.getPackageCode());
        entity.setName(dto.getName());
        return entity;
    }

    private PackageDTO entityToDto(Package entity){
        List<IndividualServiceDTO> individualServiceDTOS = new ArrayList<>();
        for(IndividualService service : entity.getServices()){
            individualServiceDTOS.add(individualService.entityToDto(service));
        }

        return PackageDTO.builder()
                .name(entity.getName())
                .packageCode(entity.getPackageCode())
                .individualServices(individualServiceDTOS)
                .id(entity.getId())
                .build();
    }

    private void validatePackageCode(String packageCode){
        if(packageRepository.existsByPackageCode(packageCode)){
            throw new IllegalArgumentException("Package code is already in use");
        };
    }

    private Package getById(Long id){
        return packageRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Package with id " + id + " not found")
        );
    }

}

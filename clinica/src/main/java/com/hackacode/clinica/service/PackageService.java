package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DiscountDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.dto.servicePackage.PackageDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IServiceMapper;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Service;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.repository.IPatientRepository;
import com.hackacode.clinica.repository.IServiceRepository;
import com.hackacode.clinica.repository.IPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PackageService implements IPackageService {

    private final IPackageRepository packageRepository;
    private final IServiceService Service;
    private final IPatientRepository patientRepository;
    private final IServiceRepository ServiceRepository;
    private final AuthService authService;
    private final IServiceMapper serviceMapper;

    @Override
    public PackageDTO save(PackageDTO packageDTO) {
        this.validatePackageCode(packageDTO.getPackageCode());
        Package packageEntity = dtoToEntity(packageDTO);
        for(Integer id : packageDTO.getServicesId()){
            Service Service = ServiceRepository.findById(Integer.toUnsignedLong(id))
                    .orElseThrow(() -> new IllegalArgumentException("Individual service with id " + id + " not found"));
            packageEntity.addService(Service);
        }
        var savedPackageEntity = packageRepository.save(packageEntity);
        return toDTO(savedPackageEntity);
    }

    @Override
    public List<PackageDTO> findAll(Pageable pageable) {
        var packages = packageRepository.findAll(pageable);
        List<PackageDTO> packageDTOS = new ArrayList<>();

        BigDecimal precioBase = BigDecimal.ZERO;
        for(Package pack : packages){
            packageDTOS.add(toDTO(pack));
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
        return toDTO(this.getById(id));
    }

    private Package dtoToEntity(PackageDTO dto){
        Package entity = new Package();
        entity.setPackageCode(dto.getPackageCode());
        return entity;
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

    private PackageDTO toDTO(Package servicePackage) {
        boolean hasHealthInsurance = false;
        if(authService.isPatient()){
            String userEmail = authService.getCurrentUserEmail();
            Patient patient = patientRepository.findByEmail(userEmail).orElseThrow(
                    () -> new ResourceNotFoundException("Patient with email " + userEmail+ " not found")
            );
            if(patient.getHealthInsurance() != null){
                hasHealthInsurance = true;
            }
        }

        BigDecimal basePrice = BigDecimal.ZERO;

        List<ServiceResponseDTO> serviceDTOS = new ArrayList<>();
        List<DiscountDTO> discountDTOS = new ArrayList<>();
        for(Service service : servicePackage.getServices()){
            basePrice = basePrice.add(service.getPrice());
            serviceDTOS.add(serviceMapper.toResponseDTO(service));
        }

        //Aplica descuento del 15% por ser paquete
        BigDecimal packageDiscount = basePrice.multiply(BigDecimal.valueOf(0.15));
        BigDecimal finalPrice = basePrice.subtract(packageDiscount);
        DiscountDTO packageDiscountDto = DiscountDTO.builder()
                .amount(packageDiscount)
                .description("Descuento por paquete")
                .build();
        discountDTOS.add(packageDiscountDto);

        //Aplica descuento adicional del 20% si tiene OS
        if (hasHealthInsurance) {
            BigDecimal healthInsuranceDiscount = finalPrice.multiply(BigDecimal.valueOf(0.20));
            finalPrice = finalPrice.subtract(healthInsuranceDiscount);
            DiscountDTO healthInsuranceDiscountDto = DiscountDTO.builder()
                    .amount(healthInsuranceDiscount)
                    .description("Descuento por obra social")
                    .build();
            discountDTOS.add(healthInsuranceDiscountDto);
        }

        return PackageDTO.builder()
                .id(servicePackage.getId())
                .services(serviceDTOS)
                .packageCode(servicePackage.getPackageCode())
                .basePrice(basePrice)
                .finalPrice(finalPrice)
                .discounts(discountDTOS)
                .build();
    }

}

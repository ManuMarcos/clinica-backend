package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PackageDTO;
import com.hackacode.clinica.model.Package;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPackageService {

    PackageDTO save(PackageDTO packageDTO);
    List<PackageDTO> findAll(Pageable pageable);
    void deleteById(Long id);
    PackageDTO findById(Long id);

}

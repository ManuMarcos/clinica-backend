package com.hackacode.clinica.repository;


import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPackageRepository extends JpaRepository<Package, Long> {
    List<Package> findByServicesContaining(Service service);
    boolean existsByPackageCode(String packageName);
}

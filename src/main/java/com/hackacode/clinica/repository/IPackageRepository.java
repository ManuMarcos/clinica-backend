package com.hackacode.clinica.repository;


import com.hackacode.clinica.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPackageRepository extends JpaRepository<Package, Long> {

    boolean existsByPackageCode(String packageName);
}

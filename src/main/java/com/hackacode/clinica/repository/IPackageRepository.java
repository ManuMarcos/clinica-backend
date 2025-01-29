package com.hackacode.clinica.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPackageRepository extends JpaRepository<Package, Long> {
}

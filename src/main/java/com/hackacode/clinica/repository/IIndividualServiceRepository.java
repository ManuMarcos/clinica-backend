package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.IndividualService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIndividualServiceRepository extends JpaRepository<IndividualService, Long> {

    boolean existsByServiceCode(String serviceCode);
    boolean existsById(Long id);
}

package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkingHourRepository extends JpaRepository<WorkingHour, Long> {
}

package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.WorkingHour;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {
}

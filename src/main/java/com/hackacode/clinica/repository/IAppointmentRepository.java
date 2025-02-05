package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndStartTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);
}

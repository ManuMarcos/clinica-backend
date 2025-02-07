package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndStartTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT a.startTime FROM Appointment a WHERE a.status = :status AND DATE(a.startTime) = :date AND a.doctor.id = :doctorId")
    List<LocalDateTime> findBookedTimesByStatusAndDateAndDoctorId(
            @Param("status") AppointmentStatus status,
            @Param("date") LocalDate date,
            @Param("doctorId") Long doctorId);
}

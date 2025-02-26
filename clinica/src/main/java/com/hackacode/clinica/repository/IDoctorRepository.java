package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d JOIN FETCH d.workingHours w JOIN d.services s " +
            "LEFT JOIN Appointment a ON a.doctor = d WHERE " +
            "w.dayOfWeek = :dayOfWeek " +
            "AND :appointmentTime BETWEEN w.timeFrom AND w.timeTo " +
            "AND s.id = :serviceId " +
            "AND (a.status != 'PENDING' OR a.startTime NOT BETWEEN :startTime AND :endTime)")
    List<Doctor> findAvailableDoctors(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                      @Param("appointmentTime") LocalTime appointmentTime,
                                      @Param("serviceId") Long serviceId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
    List<Doctor> findByServices_id(Long serviceId);
    Page<Doctor> findBySpecialityId(Long specialityId, Pageable pageable);
    Page<Doctor> findByUser_NameContaining(String name, Pageable pageable);
    Page<Doctor> findBySpecialityIdAndUser_NameContaining(Long speciality_specialityId, String name, Pageable pageable);
    Page<Doctor> findByUser_NameContainingIgnoreCaseOrSpecialityId(String name, Long specialityId, Pageable pageable);
    boolean existsBySpecialityId(Long specialityId);
}

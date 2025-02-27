package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IWorkingHourRepository extends JpaRepository<WorkingHour, Long> {
    @Query("SELECT w FROM WorkingHour w WHERE w.doctor.id = :doctorId " +
            "AND w.dayOfWeek = :dayOfWeek " +
            "AND (w.timeFrom < :timeTo AND w.timeTo > :timeFrom)")
    List<WorkingHour> findOverlappingWorkingHours(Long doctorId, DayOfWeek dayOfWeek,
                                                  LocalTime timeFrom, LocalTime timeTo);
}

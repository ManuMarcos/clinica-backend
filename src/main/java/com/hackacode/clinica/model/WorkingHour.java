package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_hour_id")
    private Long workingHourId;
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @Column(name = "time_from")
    private LocalTime timeFrom;
    @Column(name = "time_to")
    private LocalTime timeTo;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}

package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

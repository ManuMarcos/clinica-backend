package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@SuperBuilder
public class Doctor extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @ManyToMany
    @JoinTable(
            name = "doctor_service",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHour> workingHours;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public void addWorkingHour(WorkingHour workingHour) {
        this.workingHours.add(workingHour);
        workingHour.setDoctor(this);
    }

    public void removeWorkingHour(WorkingHour workingHour) {
        this.workingHours.remove(workingHour);
        workingHour.setDoctor(null);
    }
}

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private int appointmentDuration = 30;

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

    public boolean removeWorkingHour(WorkingHour workingHour) {
        if(this.workingHours.removeIf(wh -> wh.getWorkingHourId().equals(workingHour.getWorkingHourId()))){
            workingHour.setDoctor(null);
            return true;
        };
        return false;
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public boolean removeService(Service service) {
        return this.services.removeIf(s -> s.getId().equals(service.getId()));
    }
}

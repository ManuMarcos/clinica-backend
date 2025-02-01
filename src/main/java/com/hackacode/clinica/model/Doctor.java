package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@SuperBuilder
public class Doctor extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    private Double salary;

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

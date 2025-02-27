package com.hackacode.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(nullable = false)
    private Integer appointmentDuration = 30;

    @ManyToMany
    @JoinTable(
            name = "doctor_service",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHour> workingHours = new ArrayList<>();


    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public void addWorkingHour(WorkingHour workingHour) {
        this.workingHours.add(workingHour);
        workingHour.setDoctor(this);
    }

    public boolean removeWorkingHour(WorkingHour workingHour) {
        if(this.workingHours.removeIf(wh -> wh.getId().equals(workingHour.getId()))){
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

    @PrePersist
    @PreUpdate
    public void setDefaultValues() {
        if (appointmentDuration == null) {
            appointmentDuration = 30; // Valor por defecto
        }
    }
}

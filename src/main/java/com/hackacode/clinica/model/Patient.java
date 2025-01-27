package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Patient extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_insurance_id", unique = true)
    private HealthInsurance healthInsurance;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations;

}

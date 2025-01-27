package com.hackacode.clinica.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private LocalDateTime issueDate;
    private LocalDateTime paymentDate;

    @OneToMany
    private List<Consultation> consultations;

}

package com.hackacode.clinica.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal price;

    @ManyToOne
    private Invoice invoice;
}

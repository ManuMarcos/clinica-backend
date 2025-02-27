package com.hackacode.clinica.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal discount;

    private BigDecimal price;

    private BigDecimal finalPrice;

    @ManyToOne
    private Invoice invoice;
}

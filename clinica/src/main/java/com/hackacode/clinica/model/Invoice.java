package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime issueDate;

    private LocalDateTime paymentDate;

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();

    private BigDecimal totalAmount;

    private BigDecimal totalDiscount;

    private InvoiceStatus status;

}

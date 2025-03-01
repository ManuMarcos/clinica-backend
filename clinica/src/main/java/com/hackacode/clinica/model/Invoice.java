package com.hackacode.clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private BigDecimal totalDiscount;

    @Column(nullable = false)
    private InvoiceStatus status;

    @ManyToOne(optional = false)
    private Patient patient;

    public void addItem(InvoiceItem item) {
        this.items.add(item);
        item.setInvoice(this);
    }

}

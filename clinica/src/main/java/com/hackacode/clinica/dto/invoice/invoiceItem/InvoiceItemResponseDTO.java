package com.hackacode.clinica.dto.invoice.invoiceItem;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemResponseDTO {
    private String description;
    private BigDecimal price;
}

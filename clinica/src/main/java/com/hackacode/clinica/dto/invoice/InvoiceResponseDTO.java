package com.hackacode.clinica.dto.invoice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.invoice.invoiceItem.InvoiceItemResponseDTO;
import com.hackacode.clinica.model.InvoiceStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceResponseDTO {

    private Long id;
    @JsonProperty("issue_date")
    private String issueDate;
    @JsonProperty("payment_date")
    private String paymentDate;
    private String status;
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
    @JsonProperty("sub_total")
    private BigDecimal subTotal;
    @JsonProperty("total_discount")
    private BigDecimal totalDiscount;
    private List<InvoiceItemResponseDTO> items;

}

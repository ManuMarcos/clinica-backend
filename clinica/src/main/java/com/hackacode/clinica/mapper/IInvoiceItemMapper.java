package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.invoice.invoiceItem.InvoiceItemResponseDTO;
import com.hackacode.clinica.model.InvoiceItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IInvoiceItemMapper {

    InvoiceItemResponseDTO toResponse(InvoiceItem invoiceItem);
}

package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.invoice.InvoiceResponseDTO;
import com.hackacode.clinica.model.Invoice;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {IInvoiceItemMapper.class})
public interface IInvoiceMapper {

    InvoiceResponseDTO toResponseDTO(Invoice invoice);

}

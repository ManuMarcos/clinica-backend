package com.hackacode.clinica.controller;


import com.hackacode.clinica.dto.invoice.InvoiceResponseDTO;
import com.hackacode.clinica.dto.page.PaginatedResponseDTO;
import com.hackacode.clinica.service.impl.InvoiceServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceServiceImpl invoiceService;


    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<InvoiceResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(invoiceService.findAll(pageable)));
    }

    @GetMapping("/{invoiceId}/export-pdf")
    public ResponseEntity<ByteArrayResource> exportPdf(@PathVariable Long invoiceId) {
        byte[] pdfBytes = invoiceService.exportToPdf(invoiceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", String.format("factura-%d.pdf", invoiceId));
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(pdfBytes));
    }






}

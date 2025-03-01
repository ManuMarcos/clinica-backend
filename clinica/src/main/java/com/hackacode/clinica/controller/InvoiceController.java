package com.hackacode.clinica.controller;


import com.hackacode.clinica.dto.invoice.InvoiceResponseDTO;
import com.hackacode.clinica.dto.page.PaginatedResponseDTO;
import com.hackacode.clinica.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<InvoiceResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(invoiceService.findAll(pageable)));
    }






}

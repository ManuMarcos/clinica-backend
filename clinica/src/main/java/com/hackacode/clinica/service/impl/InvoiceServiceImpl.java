package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.dto.invoice.InvoiceResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IInvoiceMapper;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.repository.IInvoiceRepository;
import com.hackacode.clinica.service.IInvoiceService;
import com.hackacode.clinica.service.IPdfExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

    private final InvoiceFactoryImpl invoiceFactory;
    private final IInvoiceRepository invoiceRepository;
    private final IInvoiceMapper invoiceMapper;
    private final IPdfExporter pdfExporter;

    @Override
    public Page<InvoiceResponseDTO> findAll(Pageable pageable) {
        Page<Invoice> invoices =  invoiceRepository.findAll(pageable);
        return invoices.map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Invoice createServiceInvoice(com.hackacode.clinica.model.Service service, Patient patient) {
        Invoice invoice = invoiceFactory.createServiceInvoice(service, patient);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice createPackageInvoice(Package servicePackage, Patient patient) {
        Invoice invoice = invoiceFactory.createPackageInvoice(servicePackage, patient);
        return invoiceRepository.save(invoice);
    }

    @Override
    public byte[] exportToPdf(Long invoiceId) {
        Invoice invoice = getById(invoiceId);
        return pdfExporter.generatePdf(invoice);
    }

    private Invoice getById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("Invoice not found"));
    }
}

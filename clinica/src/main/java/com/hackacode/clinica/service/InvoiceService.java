package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.repository.IInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService{

    private final InvoiceFactory invoiceFactory;
    private final IInvoiceRepository invoiceRepository;

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
}

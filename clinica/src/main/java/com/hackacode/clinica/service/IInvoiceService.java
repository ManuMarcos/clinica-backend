package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.invoice.InvoiceResponseDTO;
import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInvoiceService {
    Page<InvoiceResponseDTO> findAll(Pageable pageable);
    Invoice createServiceInvoice(Service service, Patient patient);
    Invoice createPackageInvoice(Package servicePackage, Patient patient);
}

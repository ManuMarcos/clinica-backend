package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Service;

import java.util.List;

public interface IInvoiceService {
    Invoice createServiceInvoice(Service service, Patient patient);
    Invoice createPackageInvoice(Package servicePackage, Patient patient);
}

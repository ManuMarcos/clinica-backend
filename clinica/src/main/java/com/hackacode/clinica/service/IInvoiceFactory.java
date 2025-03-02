package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Package;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Service;

public interface IInvoiceFactory {
    Invoice createServiceInvoice(Service service, Patient patient);
    Invoice createPackageInvoice(Package servicePackage, Patient patient);
}

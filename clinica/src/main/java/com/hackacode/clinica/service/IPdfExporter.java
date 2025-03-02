package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;

public interface IPdfExporter {

    byte[] generatePdf(Appointment appointment);
    byte[] generatePdf(Invoice invoice);
}

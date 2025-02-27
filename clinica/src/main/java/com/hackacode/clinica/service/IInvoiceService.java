package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.Patient;

import java.util.List;

public interface IInvoiceService {
    Invoice createInvoice(Patient patient, List<Appointment> appointments);
}

package com.hackacode.clinica.service;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.InvoiceItem;
import com.hackacode.clinica.model.Patient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService{

    @Override
    public Invoice createInvoice(Patient patient, List<Appointment> appointments) {
        return null;
    }
}

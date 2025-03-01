package com.hackacode.clinica.service;

import com.hackacode.clinica.config.AppConstants;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.model.Package;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@org.springframework.stereotype.Service
public class InvoiceFactory {

    public Invoice createServiceInvoice(Service service, Patient patient){
        Invoice invoice = createInvoice(patient);
        addService(invoice, service);
        applyDiscounts(invoice, patient);
        return invoice;
    }

    public Invoice createPackageInvoice(Package servicePackage, Patient patient){
        Invoice invoice = createInvoice(patient);
        for(Service service : servicePackage.getServices()){
            addService(invoice, service);
        }
        applyDiscounts(invoice, patient);
        return invoice;
    }

    public void applyDiscounts(Invoice invoice, Patient patient){
        if(invoice.getItems().size() > 1){
            InvoiceItem item = new InvoiceItem();
            BigDecimal packageDiscount = invoice.getSubTotal().multiply(AppConstants.PACKAGE_DISCOUNT);
            item.setPrice(packageDiscount.negate());
            item.setDescription("Descuento por paquete (15%)");
            invoice.addItem(item);
            invoice.setTotalDiscount(invoice.getTotalDiscount().add(packageDiscount));
            invoice.setTotalAmount(invoice.getTotalAmount().subtract(packageDiscount));
        }

        if(patient.getHealthInsurance() != null){
            InvoiceItem item = new InvoiceItem();
            BigDecimal healthInsuranceDiscount = invoice.getSubTotal().multiply(AppConstants.HEALTH_INSURANCE_DISCOUNT);
            item.setPrice(healthInsuranceDiscount.negate());
            item.setDescription(String.format("Descuento por obra social %s (20%%)",
                    patient.getHealthInsurance().getName()));
            invoice.addItem(item);
            invoice.setTotalDiscount(invoice.getTotalDiscount().add(healthInsuranceDiscount));
            invoice.setTotalAmount(invoice.getTotalAmount().subtract(healthInsuranceDiscount));
        }
    }

    private Invoice createInvoice(Patient patient){
        Invoice invoice = new Invoice();
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setSubTotal(BigDecimal.ZERO);
        invoice.setTotalAmount(BigDecimal.ZERO);
        invoice.setTotalDiscount(BigDecimal.ZERO);
        invoice.setPatient(patient);
        invoice.setStatus(InvoiceStatus.PENDING);
        return invoice;
    }

    private void addService(Invoice invoice, Service service){
        InvoiceItem item = InvoiceItem.builder()
                .price(service.getPrice())
                .description(service.getName())
                .build();
        invoice.addItem(item);
        invoice.setSubTotal(invoice.getSubTotal().add(service.getPrice()));
        invoice.setTotalAmount(invoice.getSubTotal());
    }
}

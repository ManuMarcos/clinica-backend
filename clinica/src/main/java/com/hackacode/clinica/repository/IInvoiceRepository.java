package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}

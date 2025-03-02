package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.model.Appointment;
import com.hackacode.clinica.model.Invoice;
import com.hackacode.clinica.model.InvoiceItem;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.service.IPdfExporter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfExporterImpl implements IPdfExporter {


    @Override
    public byte[] generatePdf(Appointment appointment) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        User patientInfo = appointment.getPatient().getUser();
        User doctorInfo = appointment.getDoctor().getUser();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument =  new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Comprobante de Turno"));
        document.add(new Paragraph("Id: " + appointment.getId()));
        document.add(new Paragraph("Paciente: " + patientInfo.getName() + " " +
                patientInfo.getSurname()));
        document.add(new Paragraph("Doctor: " + doctorInfo.getName() + " " + doctorInfo.getSurname()));
        document.add(new Paragraph("Servicio: " + appointment.getService().getName()));
        document.add(new Paragraph("Fecha: " + formatDate(appointment.getStartTime())));
        document.close();
        return baos.toByteArray();
    }

    @Override
    public byte[] generatePdf(Invoice invoice) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument =  new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //Titulo de la factura
        document.add(new Paragraph("MEDPOINT CLINIC").setTextAlignment(TextAlignment.CENTER)
                .setBold());
        Table header = new Table(2);
        header.setWidth(UnitValue.createPercentValue(100));
        header.addCell(new Cell().add(new Paragraph("Factura de compra"))
                .setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        header.addCell(new Cell().add(new Paragraph("Fecha: " + formatDate(invoice.getIssueDate()))
                .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        document.add(header);
        document.add(new Paragraph(" "));

        //A quien se le factura
        User userInfo = invoice.getPatient().getUser();
        document.add(new Paragraph("Cliente "));
        document.add(new LineSeparator(new SolidLine(1)));
        document.add(new Paragraph("Nombre: " + userInfo.getName()));
        document.add(new Paragraph("Apellido: " + userInfo.getSurname()));
        document.add(new Paragraph("DNI: " + userInfo.getDni()));
        document.add(new Paragraph("Email: " + userInfo.getEmail()));

        //Items de la factura
        document.add(new LineSeparator(new SolidLine(1)));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        Table linesTable = new Table(2);
        linesTable.setWidth(UnitValue.createPercentValue(100));

        //Headers de la tabla
        linesTable.addCell(new Cell().add(new Paragraph("Descripción"))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER).setBold());
        linesTable.addCell(new Cell().add(new Paragraph("Precio"))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER).setBold());

        for(InvoiceItem item : invoice.getItems()) {
            Cell description = new Cell().add(new Paragraph(item.getDescription())
                    .setTextAlignment(TextAlignment.LEFT));
            Cell price = new Cell().add(new Paragraph("$ " + item.getPrice().toString()))
                    .setTextAlignment(TextAlignment.RIGHT);
            linesTable.addCell(description);
            linesTable.addCell(price);
        }
        document.add(linesTable);

        //Precios finales
        document.add(new Paragraph());
        document.add(new Paragraph("Subtotal: $ " +
                invoice.getSubTotal()).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Descuento: $ "
                + invoice.getTotalDiscount().negate()).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Total: $ "
                + invoice.getTotalAmount()).setTextAlignment(TextAlignment.RIGHT));

        document.close();

        return baos.toByteArray();
    }

    private String formatDate (LocalDateTime date){
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }
}

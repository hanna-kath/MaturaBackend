package at.spengergasse.spengermed.controller;


import at.spengergasse.spengermed.model.Invoice;
import at.spengergasse.spengermed.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/invoice")
@RestController
@CrossOrigin
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    @ResponseBody
    public Iterable<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable UUID id) {
        return invoiceRepository
                .findById(id)
                .map(invoice -> ResponseEntity.ok().body(invoice))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) {
        invoice.setId(null);
        var saved = invoiceRepository.save(invoice);
        return ResponseEntity
                .created(URI.create("/api/invoice/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable(value = "id") UUID invoiceId, @Valid @RequestBody Invoice invoiceDetails) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> {
                    invoice.setIdentifier(invoiceDetails.getIdentifier());
                    invoice.setStatus(invoiceDetails.getStatus());
                    invoice.setCancelledReason(invoiceDetails.getCancelledReason());
                    invoice.setType(invoiceDetails.getType());
                    invoice.setSubject(invoiceDetails.getSubject());
                    invoice.setRecipient(invoiceDetails.getRecipient());
                    invoice.setDate(invoiceDetails.getDate());
                    invoice.setCreation(invoiceDetails.getCreation());
                    invoice.setPeriodDate(invoiceDetails.getPeriodDate());
                    invoice.setPeriodPeriod(invoiceDetails.getPeriodPeriod());
                    invoice.setIssuer(invoiceDetails.getIssuer());
                    invoice.setAccount(invoiceDetails.getAccount());
                    invoice.setTotalPriceComponent(invoiceDetails.getTotalPriceComponent());
                    invoice.setTotalNet(invoiceDetails.getTotalNet());
                    invoice.setTotalGross(invoiceDetails.getTotalGross());
                    invoice.setPaymentTerms(invoiceDetails.getPaymentTerms());
                    invoice.setLineItem(invoiceDetails.getLineItem());
                    invoice.setParticipant(invoiceDetails.getParticipant());
                    invoice.setNote(invoiceDetails.getNote());
                    Invoice updatedInvoice = invoiceRepository.save(invoice);
                    return ResponseEntity.ok(updatedInvoice);
                }).orElseGet( () -> createInvoice(invoiceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable(value = "id") UUID invoiceId) {
        return invoiceRepository.findById(invoiceId).map(invoice -> {
            invoiceRepository.delete(invoice);
            return ResponseEntity.ok().<Invoice>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

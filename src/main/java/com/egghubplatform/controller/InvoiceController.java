package com.egghubplatform.controller;

import com.egghubplatform.domain.Invoice;
import com.egghubplatform.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveInvoice(@RequestParam UUID eggOrderId, @RequestBody Invoice invoice) {
        String result = invoiceService.saveInvoice(eggOrderId, invoice);
        if (result.startsWith("404:")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.substring(4));
        } else if (result.startsWith("400:")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.substring(4));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result.substring(4));
    }

    @GetMapping("/bulk-buyer/{tin}")
    public ResponseEntity<List<Invoice>> getInvoicesByBulkBuyer(@PathVariable String tin) {
        List<Invoice> invoices = invoiceService.getInvoicesByBulkBuyerTin(tin);
        return ResponseEntity.ok(invoices);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable UUID id) {
        String result = invoiceService.deleteInvoice(id);
        if (result.startsWith("404:")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.substring(4));
        }
        return ResponseEntity.ok(result.substring(4));
    }
}
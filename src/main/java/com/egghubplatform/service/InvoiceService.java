package com.egghubplatform.service;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.Invoice;
import com.egghubplatform.domain.OrderStatus;
import com.egghubplatform.repository.EggOrderRepository;
import com.egghubplatform.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EggOrderRepository eggOrderRepository;

    public String saveInvoice(UUID eggOrderId, Invoice invoice) {
        Optional<EggOrder> orderOpt = eggOrderRepository.findById(eggOrderId);
        if (orderOpt.isEmpty()) {
            return "404:The egg order with that id does not exist";
        }

        EggOrder order = orderOpt.get();
        if (order.getStatus() != OrderStatus.DELIVERED) {
            return "400:Invoices are only for DELIVERED egg orders";
        }

        invoice.setEggOrder(order);
        invoice.setIssuedDate(LocalDate.now());
        invoiceRepository.save(invoice);
        return "201:Invoice saved successfully";
    }

    public List<Invoice> getInvoicesByBulkBuyerTin(String tin) {
        return invoiceRepository.findInvoicesByBulkBuyerTin(tin);
    }

    public String deleteInvoice(UUID id) {
        if (!invoiceRepository.existsById(id)) {
            return "404:The invoice with that id does not exist";
        }
        invoiceRepository.deleteById(id);
        return "200:Invoice deleted successfully";
    }
}
package com.egghubplatform.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String paymentMethod;
    private String note;
    private int amountRwf;
    private LocalDate issuedDate;

    @ManyToOne
    @JoinColumn(name = "egg_order_id")
    private EggOrder eggOrder;

    public Invoice() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getAmountRwf() { return amountRwf; }
    public void setAmountRwf(int amountRwf) { this.amountRwf = amountRwf; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public EggOrder getEggOrder() { return eggOrder; }
    public void setEggOrder(EggOrder eggOrder) { this.eggOrder = eggOrder; }
}
package com.egghubplatform.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "egg_order")
public class EggOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int trays;
    private double pricePerTray;

    @ManyToOne
    @JoinColumn(name = "bulk_buyer_id")
    private BulkBuyer bulkBuyer;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    public EggOrder() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public int getTrays() { return trays; }
    public void setTrays(int trays) { this.trays = trays; }

    public double getPricePerTray() { return pricePerTray; }
    public void setPricePerTray(double pricePerTray) { this.pricePerTray = pricePerTray; }

    public BulkBuyer getBulkBuyer() { return bulkBuyer; }
    public void setBulkBuyer(BulkBuyer bulkBuyer) { this.bulkBuyer = bulkBuyer; }

    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }
}
package com.egghubplatform.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bulk_buyer")
public class BulkBuyer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String businessName;
    private String contactName;

    @Column(unique = true)
    private String tin;

    private String phone;
    private String city;

    public BulkBuyer() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getTin() { return tin; }
    public void setTin(String tin) { this.tin = tin; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
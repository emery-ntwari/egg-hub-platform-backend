package com.egghubplatform.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "farm")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int farmNumber;
    private String name;

    public Farm() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getFarmNumber() { return farmNumber; }
    public void setFarmNumber(int farmNumber) { this.farmNumber = farmNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
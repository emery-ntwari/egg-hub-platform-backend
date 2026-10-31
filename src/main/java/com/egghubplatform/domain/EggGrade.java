package com.egghubplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "egg_grade")
public class EggGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "eggGrades")
    @JsonIgnore
    private List<Farmer> farmers;

    public EggGrade() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Farmer> getFarmers() { return farmers; }
    public void setFarmers(List<Farmer> farmers) { this.farmers = farmers; }
}
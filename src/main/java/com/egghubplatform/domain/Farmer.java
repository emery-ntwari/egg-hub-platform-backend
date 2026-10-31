package com.egghubplatform.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "farmer")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @ManyToMany
    @JoinTable(
        name = "farmer_egg_grade",
        joinColumns = @JoinColumn(name = "farmer_id"),
        inverseJoinColumns = @JoinColumn(name = "egg_grade_id")
    )
    private List<EggGrade> eggGrades;

    public Farmer() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Farm getFarm() { return farm; }
    public void setFarm(Farm farm) { this.farm = farm; }

    public List<EggGrade> getEggGrades() { return eggGrades; }
    public void setEggGrades(List<EggGrade> eggGrades) { this.eggGrades = eggGrades; }
}
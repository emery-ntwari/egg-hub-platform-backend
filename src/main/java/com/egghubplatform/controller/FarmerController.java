package com.egghubplatform.controller;

import com.egghubplatform.domain.Farmer;
import com.egghubplatform.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveFarmer(@RequestBody Farmer farmer) {
        Farmer saved = farmerService.saveFarmer(farmer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Farmer>> getAllFarmers() {
        return ResponseEntity.ok(farmerService.getAllFarmers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFarmerById(@PathVariable UUID id) {
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        if (farmer.isPresent()) {
            return ResponseEntity.ok(farmer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farmer with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateFarmer(@PathVariable UUID id, @RequestBody Farmer farmer) {
        Farmer updated = farmerService.updateFarmer(id, farmer);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farmer with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteFarmer(@PathVariable UUID id) {
        boolean deleted = farmerService.deleteFarmer(id);
        if (deleted) {
            return ResponseEntity.ok("Farmer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farmer with that id does not exist");
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<Farmer>> saveAllFarmers(@RequestBody List<Farmer> farmers) {
        List<Farmer> saved = farmerService.saveAllFarmers(farmers);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/by-egg-grade")
    public ResponseEntity<List<Farmer>> getFarmersByEggGrade(@RequestParam String name) {
        List<Farmer> farmers = farmerService.getFarmersByEggGrade(name);
        return ResponseEntity.ok(farmers);
    }

    @PutMapping("/{farmerId}/egg-grades/{eggGradeId}")
    public ResponseEntity<Object> addEggGradeToFarmer(@PathVariable UUID farmerId, @PathVariable UUID eggGradeId) {
        String response = farmerService.addEggGradeToFarmer(farmerId, eggGradeId);
        if (response.startsWith("404:")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.substring(4));
        } else if (response.startsWith("409:")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response.substring(4));
        }
        return ResponseEntity.ok(response.substring(4));
    }

@PatchMapping("/{farmerId}/farm")
    public ResponseEntity<Object> moveFarmerToFarm(@PathVariable UUID farmerId, @RequestParam int farmNumber) {
        String response = farmerService.moveFarmerToFarm(farmerId, farmNumber);
        if (response.startsWith("404:")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.substring(4));
        } else if (response.startsWith("409:")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response.substring(4));
        }
        return ResponseEntity.ok(response.substring(4));
    }
    
    
}
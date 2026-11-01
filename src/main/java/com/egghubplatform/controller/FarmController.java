package com.egghubplatform.controller;

import com.egghubplatform.domain.Farm;
import com.egghubplatform.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveFarm(@RequestBody Farm farm) {
        Farm saved = farmService.saveFarm(farm);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Farm>> getAllFarms() {
        return ResponseEntity.ok(farmService.getAllFarms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFarmById(@PathVariable UUID id) {
        Optional<Farm> farm = farmService.getFarmById(id);
        if (farm.isPresent()) {
            return ResponseEntity.ok(farm.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farm with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateFarm(@PathVariable UUID id, @RequestBody Farm farm) {
        Farm updated = farmService.updateFarm(id, farm);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farm with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteFarm(@PathVariable UUID id) {
        boolean deleted = farmService.deleteFarm(id);
        if (deleted) {
            return ResponseEntity.ok("Farm deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The farm with that id does not exist");
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<Farm>> saveAllFarms(@RequestBody List<Farm> farms) {
        List<Farm> saved = farmService.saveAllFarms(farms);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Farm>> getFreeFarms() {
        List<Farm> freeFarms = farmService.getFreeFarms();
        return ResponseEntity.ok(freeFarms);
    }

    
}
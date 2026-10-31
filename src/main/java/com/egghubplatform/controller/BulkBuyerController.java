package com.egghubplatform.controller;

import com.egghubplatform.domain.BulkBuyer;
import com.egghubplatform.service.BulkBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bulk-buyers")
public class BulkBuyerController {

    @Autowired
    private BulkBuyerService bulkBuyerService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveBulkBuyer(@RequestBody BulkBuyer bulkBuyer) {
        BulkBuyer saved = bulkBuyerService.saveBulkBuyer(bulkBuyer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BulkBuyer>> getAllBulkBuyers() {
        return ResponseEntity.ok(bulkBuyerService.getAllBulkBuyers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBulkBuyerById(@PathVariable UUID id) {
        Optional<BulkBuyer> buyer = bulkBuyerService.getBulkBuyerById(id);
        if (buyer.isPresent()) {
            return ResponseEntity.ok(buyer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The bulk buyer with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBulkBuyer(@PathVariable UUID id, @RequestBody BulkBuyer bulkBuyer) {
        BulkBuyer updated = bulkBuyerService.updateBulkBuyer(id, bulkBuyer);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The bulk buyer with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBulkBuyer(@PathVariable UUID id) {
        boolean deleted = bulkBuyerService.deleteBulkBuyer(id);
        if (deleted) {
            return ResponseEntity.ok("Bulk buyer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The bulk buyer with that id does not exist");
        }
    }
}
package com.egghubplatform.controller;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.OrderStatus;
import com.egghubplatform.service.EggOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/egg-orders")
public class EggOrderController {

    @Autowired
    private EggOrderService eggOrderService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveEggOrder(@RequestBody EggOrder eggOrder) {
        EggOrder saved = eggOrderService.saveEggOrder(eggOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EggOrder>> getAllEggOrders() {
        return ResponseEntity.ok(eggOrderService.getAllEggOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEggOrderById(@PathVariable UUID id) {
        Optional<EggOrder> order = eggOrderService.getEggOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg order with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEggOrder(@PathVariable UUID id, @RequestBody EggOrder eggOrder) {
        EggOrder updated = eggOrderService.updateEggOrder(id, eggOrder);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg order with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEggOrder(@PathVariable UUID id) {
        boolean deleted = eggOrderService.deleteEggOrder(id);
        if (deleted) {
            return ResponseEntity.ok("Egg order deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg order with that id does not exist");
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<EggOrder>> getEggOrdersByStatus(@RequestParam OrderStatus status) {
        List<EggOrder> orders = eggOrderService.getEggOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/between")
    public ResponseEntity<List<EggOrder>> getEggOrdersBetween(@RequestParam LocalDate start, @RequestParam LocalDate end) {
            List<EggOrder> orders = eggOrderService.getEggOrdersBetweenDates(start, end);
            return ResponseEntity.ok(orders);
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<EggOrder>> saveAllEggOrders(@RequestBody List<EggOrder> eggOrders) {
            List<EggOrder> saved = eggOrderService.saveAllEggOrders(eggOrders);
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
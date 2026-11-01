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
        Object result = eggOrderService.saveEggOrderValidated(eggOrder);
        if (result instanceof String) {
            String err = (String) result;
            if (err.contains("past")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
            } else if (err.contains("already has an order")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
    String result = eggOrderService.deleteEggOrder(id);
    if (result.startsWith("404:")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.substring(4));
    } else if (result.startsWith("409:")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result.substring(4));
    }
    return ResponseEntity.ok(result.substring(4));
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

@PatchMapping("/{id}/status")
public ResponseEntity<Object> updateOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
    String response = eggOrderService.updateOrderStatus(id, status);
    if (response.startsWith("404:")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.substring(4));
    } else if (response.startsWith("400:")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.substring(4));
    }
    return ResponseEntity.ok(response.substring(4));
}
}
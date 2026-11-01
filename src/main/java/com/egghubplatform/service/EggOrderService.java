package com.egghubplatform.service;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.OrderStatus;
import com.egghubplatform.repository.BulkBuyerRepository;
import com.egghubplatform.repository.EggOrderRepository;
import com.egghubplatform.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EggOrderService {

    @Autowired
    private EggOrderRepository eggOrderRepository;

    @Autowired
    private BulkBuyerRepository bulkBuyerRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    public EggOrder saveEggOrder(EggOrder eggOrder) {
        // Safely link BulkBuyer if provided via ID
        if (eggOrder.getBulkBuyer() != null && eggOrder.getBulkBuyer().getId() != null) {
            bulkBuyerRepository.findById(eggOrder.getBulkBuyer().getId()).ifPresent(eggOrder::setBulkBuyer);
        }
        // Safely link Farmer if provided via ID
        if (eggOrder.getFarmer() != null && eggOrder.getFarmer().getId() != null) {
            farmerRepository.findById(eggOrder.getFarmer().getId()).ifPresent(eggOrder::setFarmer);
        }
        return eggOrderRepository.save(eggOrder);
    }

    public List<EggOrder> getAllEggOrders() {
        return eggOrderRepository.findAll();
    }

    public Optional<EggOrder> getEggOrderById(UUID id) {
        return eggOrderRepository.findById(id);
    }

    public EggOrder updateEggOrder(UUID id, EggOrder updatedOrder) {
        return eggOrderRepository.findById(id).map(order -> {
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setStatus(updatedOrder.getStatus());
            order.setTrays(updatedOrder.getTrays());
            order.setPricePerTray(updatedOrder.getPricePerTray());

            // Safely link or clear BulkBuyer
            if (updatedOrder.getBulkBuyer() != null && updatedOrder.getBulkBuyer().getId() != null) {
                bulkBuyerRepository.findById(updatedOrder.getBulkBuyer().getId()).ifPresent(order::setBulkBuyer);
            } else {
                order.setBulkBuyer(null);
            }

            // Safely link or clear Farmer
            if (updatedOrder.getFarmer() != null && updatedOrder.getFarmer().getId() != null) {
                farmerRepository.findById(updatedOrder.getFarmer().getId()).ifPresent(order::setFarmer);
            } else {
                order.setFarmer(null);
            }

            return eggOrderRepository.save(order);
        }).orElse(null);
    }

    public boolean deleteEggOrder(UUID id) {
        if (eggOrderRepository.existsById(id)) {
            eggOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EggOrder> getEggOrdersByStatus(OrderStatus status) {
        return eggOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    public List<EggOrder> getEggOrdersBetweenDates(LocalDate start, LocalDate end) {
        return eggOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    public List<EggOrder> saveAllEggOrders(List<EggOrder> eggOrders) {
            for (EggOrder order : eggOrders) {
                        if (order.getBulkBuyer() != null && order.getBulkBuyer().getId() != null) {
                                        bulkBuyerRepository.findById(order.getBulkBuyer().getId()).ifPresent(order::setBulkBuyer);
                        }
                        if (order.getFarmer() != null && order.getFarmer().getId() != null) {
                                        farmerRepository.findById(order.getFarmer().getId()).ifPresent(order::setFarmer);
                        }
            }
                return eggOrderRepository.saveAll(eggOrders);
    }
}
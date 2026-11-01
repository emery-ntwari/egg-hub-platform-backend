package com.egghubplatform.service;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.OrderStatus;
import com.egghubplatform.repository.BulkBuyerRepository;
import com.egghubplatform.repository.EggOrderRepository;
import com.egghubplatform.repository.FarmerRepository;
import com.egghubplatform.repository.InvoiceRepository;

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

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Object saveEggOrderValidated(EggOrder eggOrder) {
 
        if (eggOrder.getOrderDate() != null && eggOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Egg order date cannot be in the past";
        }
        
        if (eggOrder.getBulkBuyer() != null && eggOrder.getBulkBuyer().getId() != null) {
            bulkBuyerRepository.findById(eggOrder.getBulkBuyer().getId()).ifPresent(eggOrder::setBulkBuyer);
        }
        if (eggOrder.getFarmer() != null && eggOrder.getFarmer().getId() != null) {
            farmerRepository.findById(eggOrder.getFarmer().getId()).ifPresent(eggOrder::setFarmer);
        }
        if (eggOrder.getFarmer() != null && eggOrder.getFarmer().getId() != null && eggOrder.getOrderDate() != null) {
            boolean hasExistingOrder = eggOrderRepository.existsByFarmerIdAndOrderDateAndStatusNot(
                eggOrder.getFarmer().getId(), eggOrder.getOrderDate(), OrderStatus.CANCELLED);
                if (hasExistingOrder){
                    return "Farmer already has an order on that date";
                }
        }
        eggOrder.setStatus(OrderStatus.PLACED);
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

        public String deleteEggOrder(UUID id) {
    if (!eggOrderRepository.existsById(id)) {
        return "404:The egg order with that id does not exist";
    }
    if (invoiceRepository.existsByEggOrderId(id)) {
        return "409:That egg order has invoices, delete them first";
    }
    eggOrderRepository.deleteById(id);
    return "200:Egg order deleted successfully";
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

    public String updateOrderStatus(UUID id, OrderStatus newStatus) {
        Optional<EggOrder> optionalOrder = eggOrderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return "404:The egg order with that id does not exist";
        }
        EggOrder order = optionalOrder.get();
        OrderStatus currentStatus = order.getStatus();
        
        boolean isValidTransition = false;
        if (currentStatus == OrderStatus.PLACED) {
            if (newStatus == OrderStatus.PICKED_UP || newStatus == OrderStatus.CANCELLED) {
                isValidTransition = true;
            }
        } else if (currentStatus == OrderStatus.PICKED_UP) {
            if (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED) {
                isValidTransition = true;
            }
            return "400:Cannot change status from " + currentStatus + " to " + newStatus;
        }
        
        order.setStatus(newStatus);
        eggOrderRepository.save(order);
        return "200:Egg order status updated successfully";
    }

}
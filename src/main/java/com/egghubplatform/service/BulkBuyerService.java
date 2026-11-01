package com.egghubplatform.service;

import com.egghubplatform.domain.BulkBuyer;
import com.egghubplatform.repository.BulkBuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BulkBuyerService {

    @Autowired
    private BulkBuyerRepository bulkBuyerRepository;

    public BulkBuyer saveBulkBuyer(BulkBuyer bulkBuyer) {
        return bulkBuyerRepository.save(bulkBuyer);
    }

    public List<BulkBuyer> getAllBulkBuyers() {
        return bulkBuyerRepository.findAll();
    }

    public Optional<BulkBuyer> getBulkBuyerById(UUID id) {
        return bulkBuyerRepository.findById(id);
    }

    public BulkBuyer updateBulkBuyer(UUID id, BulkBuyer updatedBuyer) {
        return bulkBuyerRepository.findById(id).map(buyer -> {
            buyer.setBusinessName(updatedBuyer.getBusinessName());
            buyer.setContactName(updatedBuyer.getContactName());
            buyer.setTin(updatedBuyer.getTin());
            buyer.setPhone(updatedBuyer.getPhone());
            buyer.setCity(updatedBuyer.getCity());
            return bulkBuyerRepository.save(buyer);
        }).orElse(null);
    }

    public boolean deleteBulkBuyer(UUID id) {
        if (bulkBuyerRepository.existsById(id)) {
            bulkBuyerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BulkBuyer> getBulkBuyersByContactName(String contactName) {
        return bulkBuyerRepository.findByContactNameIgnoreCaseOrderByBusinessNameAsc(contactName);
    }

    public List<BulkBuyer> saveAllBulkBuyers(List<BulkBuyer> bulkBuyers) {
            return bulkBuyerRepository.saveAll(bulkBuyers);
    }
}
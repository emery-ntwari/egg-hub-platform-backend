package com.egghubplatform.service;

import com.egghubplatform.domain.Farm;
import com.egghubplatform.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    public Farm saveFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Optional<Farm> getFarmById(UUID id) {
        return farmRepository.findById(id);
    }

    public Farm updateFarm(UUID id, Farm updatedFarm) {
        return farmRepository.findById(id).map(farm -> {
            farm.setFarmNumber(updatedFarm.getFarmNumber());
            farm.setName(updatedFarm.getName());
            return farmRepository.save(farm);
        }).orElse(null);
    }

    public boolean deleteFarm(UUID id) {
        if (farmRepository.existsById(id)) {
            farmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Farm> saveAllFarms(List<Farm> farms) {
        return farmRepository.saveAll(farms);
    }
}
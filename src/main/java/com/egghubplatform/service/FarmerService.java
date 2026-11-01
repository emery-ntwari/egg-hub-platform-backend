package com.egghubplatform.service;

import com.egghubplatform.domain.EggGrade;
import com.egghubplatform.domain.Farmer;
import com.egghubplatform.repository.EggGradeRepository;
import com.egghubplatform.repository.FarmRepository;
import com.egghubplatform.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egghubplatform.domain.Farm;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private EggGradeRepository eggGradeRepository;

    public Farmer saveFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    public Optional<Farmer> getFarmerById(UUID id) {
        return farmerRepository.findById(id);
    }

    public Farmer updateFarmer(UUID id, Farmer updatedFarmer) {
        return farmerRepository.findById(id).map(farmer -> {
            farmer.setFirstName(updatedFarmer.getFirstName());
            farmer.setLastName(updatedFarmer.getLastName());
            farmer.setEmail(updatedFarmer.getEmail());
            farmer.setPhone(updatedFarmer.getPhone());

            // Safely link Farm if provided
            if (updatedFarmer.getFarm() != null && updatedFarmer.getFarm().getId() != null) {
                farmRepository.findById(updatedFarmer.getFarm().getId()).ifPresent(farmer::setFarm);
            }

            // Safely link EggGrades if provided
            if (updatedFarmer.getEggGrades() != null && !updatedFarmer.getEggGrades().isEmpty()) {
                List<UUID> gradeIds = updatedFarmer.getEggGrades().stream().map(EggGrade::getId).toList();
                List<EggGrade> grades = eggGradeRepository.findAllById(gradeIds);
                farmer.setEggGrades(grades);
            }

            return farmerRepository.save(farmer);
        }).orElse(null);
    }

    public boolean deleteFarmer(UUID id) {
        if (farmerRepository.existsById(id)) {
            farmerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Farmer> saveAllFarmers(List<Farmer> farmers) {
        for (Farmer farmer : farmers) {
            if (farmer.getFarm() != null && farmer.getFarm().getId() != null) {
                farmRepository.findById(farmer.getFarm().getId()).ifPresent(farmer::setFarm);
            }
            if (farmer.getEggGrades() != null && !farmer.getEggGrades().isEmpty()) {
                List<UUID> gradeIds = farmer.getEggGrades().stream()
                    .map(EggGrade::getId)
                    .filter(Objects::nonNull)
                    .toList();
                farmer.setEggGrades(eggGradeRepository.findAllById(gradeIds));
            }
        }
        return farmerRepository.saveAll(farmers);
    }

    public List<Farmer> getFarmersByEggGrade(String name) {
        return farmerRepository.findByEggGradeNameIgnoreCase(name);
    }

    public String addEggGradeToFarmer(UUID farmerId, UUID eggGradeId) {
        Optional<Farmer> farmerOpt = farmerRepository.findById(farmerId);
        if (farmerOpt.isEmpty()) {
            return "404:The farmer with that id does not exist";
        }
        
        Optional<EggGrade> gradeOpt = eggGradeRepository.findById(eggGradeId);
        if (gradeOpt.isEmpty()) {
            return "404:That egg grade does not exist";
        }
        Farmer farmer = farmerOpt.get();
        EggGrade grade = gradeOpt.get();

        if (farmer.getEggGrades().contains(grade)) {
            return "409:The farmer already has that egg grade";
        }

        farmer.getEggGrades().add(grade);
        farmerRepository.save(farmer);
        return "200:Egg grade added to the farmer";
    }
    
    public String moveFarmerToFarm(UUID farmerId, int farmNumber) {
    Optional<Farmer> farmerOpt = farmerRepository.findById(farmerId);
    if (farmerOpt.isEmpty()) {
        return "404:The farmer with that id does not exist";
    }

    Optional<Farm> newFarmOpt = farmRepository.findByFarmNumber(farmNumber);
    if (newFarmOpt.isEmpty()) {
        return "404:The farm with that farmNumber does not exist";
    }

    Farm newFarm = newFarmOpt.get();

    // Check if another farmer already occupies this farm
    List<Farmer> allFarmers = farmerRepository.findAll();
    for (Farmer f : allFarmers) {
        if (f.getFarm() != null && f.getFarm().getId().equals(newFarm.getId()) && !f.getId().equals(farmerId)) {
            return "409:That farm is taken by another farmer";
        }
    }

    Farmer farmer = farmerOpt.get();
    farmer.setFarm(newFarm);
    farmerRepository.save(farmer);
    return "200:Farm changed successfully";
}
}
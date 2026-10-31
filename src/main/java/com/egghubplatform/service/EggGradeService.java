package com.egghubplatform.service;

import com.egghubplatform.domain.EggGrade;
import com.egghubplatform.repository.EggGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EggGradeService {

    @Autowired
    private EggGradeRepository eggGradeRepository;

    public EggGrade saveEggGrade(EggGrade eggGrade) {
        return eggGradeRepository.save(eggGrade);
    }

    public List<EggGrade> getAllEggGrades() {
        return eggGradeRepository.findAll();
    }

    public Optional<EggGrade> getEggGradeById(UUID id) {
        return eggGradeRepository.findById(id);
    }

    public EggGrade updateEggGrade(UUID id, EggGrade updatedEggGrade) {
        return eggGradeRepository.findById(id).map(grade -> {
            grade.setName(updatedEggGrade.getName());
            return eggGradeRepository.save(grade);
        }).orElse(null);
    }

    public boolean deleteEggGrade(UUID id) {
        if (eggGradeRepository.existsById(id)) {
            eggGradeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
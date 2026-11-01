package com.egghubplatform.controller;

import com.egghubplatform.domain.EggGrade;
import com.egghubplatform.service.EggGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/egg-grades")
public class EggGradeController {

    @Autowired
    private EggGradeService eggGradeService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveEggGrade(@RequestBody EggGrade eggGrade) {
        EggGrade saved = eggGradeService.saveEggGrade(eggGrade);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EggGrade>> getAllEggGrades() {
        return ResponseEntity.ok(eggGradeService.getAllEggGrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEggGradeById(@PathVariable UUID id) {
        Optional<EggGrade> grade = eggGradeService.getEggGradeById(id);
        if (grade.isPresent()) {
            return ResponseEntity.ok(grade.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg grade with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEggGrade(@PathVariable UUID id, @RequestBody EggGrade eggGrade) {
        EggGrade updated = eggGradeService.updateEggGrade(id, eggGrade);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg grade with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEggGrade(@PathVariable UUID id) {
        boolean deleted = eggGradeService.deleteEggGrade(id);
        if (deleted) {
            return ResponseEntity.ok("Egg grade deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The egg grade with that id does not exist");
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<EggGrade>> saveAllEggGrades(@RequestBody List<EggGrade> eggGrades) {
        List<EggGrade> saved = eggGradeService.saveAllEggGrades(eggGrades);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
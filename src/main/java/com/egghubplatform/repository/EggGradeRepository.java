package com.egghubplatform.repository;

import com.egghubplatform.domain.EggGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface EggGradeRepository extends JpaRepository<EggGrade, UUID> {
    @Query("SELECT g FROM EggGrade g WHERE g.farmers IS EMPTY")
    List<EggGrade> findUnusedEggGrades();
}
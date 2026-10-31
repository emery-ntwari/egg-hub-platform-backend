package com.egghubplatform.repository;

import com.egghubplatform.domain.EggGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EggGradeRepository extends JpaRepository<EggGrade, UUID> {
}
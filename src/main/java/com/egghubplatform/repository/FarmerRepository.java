package com.egghubplatform.repository;

import com.egghubplatform.domain.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {
    @Query("SELECT f FROM Farmer f JOIN f.eggGrades g WHERE LOWER(g.name) = LOWER(:name)")
    List<Farmer> findByEggGradeNameIgnoreCase(@Param("name") String name);
}
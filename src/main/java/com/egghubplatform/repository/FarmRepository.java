package com.egghubplatform.repository;

import com.egghubplatform.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Optional<Farm> findByFarmNumber(int farmNumber);

    @Query("SELECT f FROM Farm f WHERE NOT EXISTS (SELECT fr FROM Farmer fr WHERE fr.farm = f) ORDER BY f.farmNumber ASC")
    List<Farm> findFreeFarms();
}
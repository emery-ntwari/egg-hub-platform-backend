package com.egghubplatform.repository;

import com.egghubplatform.domain.BulkBuyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BulkBuyerRepository extends JpaRepository<BulkBuyer, UUID> {
    boolean existsByTin(String tin);

    List<BulkBuyer> findByContactNameIgnoreCaseOrderByBusinessNameAsc(String contactName);

    @Query("SELECT DISTINCT o.bulkBuyer FROM EggOrder o WHERE o.farmer.id = :farmerId")
    List<BulkBuyer> findBulkBuyersByFarmerId(@Param("farmerId") UUID farmerId);
}
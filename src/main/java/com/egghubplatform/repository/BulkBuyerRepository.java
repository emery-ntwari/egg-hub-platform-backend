package com.egghubplatform.repository;

import com.egghubplatform.domain.BulkBuyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BulkBuyerRepository extends JpaRepository<BulkBuyer, UUID> {
    boolean existsByTin(String tin);
}
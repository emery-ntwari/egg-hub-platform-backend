package com.egghubplatform.repository;

import com.egghubplatform.domain.EggOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EggOrderRepository extends JpaRepository<EggOrder, UUID> {
}
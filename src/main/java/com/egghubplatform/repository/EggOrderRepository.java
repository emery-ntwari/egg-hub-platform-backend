package com.egghubplatform.repository;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EggOrderRepository extends JpaRepository<EggOrder, UUID> {

    List<EggOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<EggOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
}
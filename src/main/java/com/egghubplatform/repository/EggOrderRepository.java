package com.egghubplatform.repository;

import com.egghubplatform.domain.EggOrder;
import com.egghubplatform.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EggOrderRepository extends JpaRepository<EggOrder, UUID> {
    boolean existsByFarmerIdAndOrderDateAndStatusNot(UUID farmerId, LocalDate orderDate, OrderStatus status);

    List<EggOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<EggOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    @Query("SELECT o.status, COUNT(o) FROM EggOrder o GROUP BY o.status")
    List<Object[]> countOrdersPerStatus();

    @Modifying
@Query("UPDATE EggOrder o SET o.status = com.egghubplatform.domain.OrderStatus.CANCELLED " +
       "WHERE o.farmer.id = :farmerId AND o.orderDate = :date " +
       "AND o.status <> com.egghubplatform.domain.OrderStatus.DELIVERED")
       int cancelOrdersForFarmerOnDate(@Param("farmerId") UUID farmerId, @Param("date") LocalDate date);
}
package com.egghubplatform.repository;

import com.egghubplatform.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    
    @Query("SELECT d FROM Invoice d WHERE d.eggOrder.bulkBuyer.tin = :tin ORDER BY d.issuedDate DESC")
    List<Invoice> findInvoicesByBulkBuyerTin(@Param("tin") String tin);

    boolean existsByEggOrderId(UUID eggOrderId);
}
package com.kick_api.repository;

import com.kick_api.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    
    List<StockTransaction> findAllByVariantIdOrderByCreatedAtDesc(Long variantId);
}

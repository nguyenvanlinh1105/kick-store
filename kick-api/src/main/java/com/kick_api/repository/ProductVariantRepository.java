package com.kick_api.repository;

import com.kick_api.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findAllByProductId(Long productId);

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);
}

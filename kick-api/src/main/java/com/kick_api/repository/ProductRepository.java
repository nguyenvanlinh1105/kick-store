package com.kick_api.repository;

import com.kick_api.entity.Product;
import com.kick_api.constant.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);

    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findAllByCategoryIdAndStatus(Long categoryId, ProductStatus status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
}

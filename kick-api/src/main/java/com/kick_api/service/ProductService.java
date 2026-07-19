package com.kick_api.service;

import com.kick_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product getProductBySlug(String slug);
    Page<Product> searchProducts(String keyword, Pageable pageable);
    void updateStock(Long variantId, int quantityChange);
    void reserveStock(Long variantId, int quantity);
    void releaseStock(Long variantId, int quantity);
}

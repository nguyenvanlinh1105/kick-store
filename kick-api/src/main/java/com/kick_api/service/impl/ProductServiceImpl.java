package com.kick_api.service.impl;

import com.kick_api.constant.ErrorCode;
import com.kick_api.constant.ProductStatus;
import com.kick_api.entity.Product;
import com.kick_api.exception.AppException;
import com.kick_api.repository.ProductRepository;
import com.kick_api.repository.ProductVariantRepository;
import com.kick_api.repository.StockTransactionRepository;
import com.kick_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kick_api.entity.ProductVariant;
import com.kick_api.entity.StockTransaction;
import com.kick_api.constant.StockTransactionType;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final StockTransactionRepository stockTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Product getProductBySlug(String slug) {
        return productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAllByStatus(ProductStatus.ACTIVE, pageable);
        }
        return productRepository.searchProducts(keyword.trim(), pageable);
    }

    @Override
    @Transactional
    public void updateStock(Long variantId, int quantityChange) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        
        int newAvailableStock = variant.getAvailableStock() + quantityChange;
        if (newAvailableStock < 0) {
            throw new AppException(ErrorCode.STOCK_INSUFFICIENT);
        }
        
        variant.setAvailableStock(newAvailableStock);
        productVariantRepository.save(variant);

        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(quantityChange > 0 ? StockTransactionType.IMPORT : StockTransactionType.EXPORT)
                .quantity(Math.abs(quantityChange))
                .note("Thay đổi trực tiếp từ quản lý kho")
                .build();
        stockTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void reserveStock(Long variantId, int quantity) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        if (variant.getAvailableStock() < quantity) {
            throw new AppException(ErrorCode.STOCK_INSUFFICIENT, "Sản phẩm " + variant.getSku() + " đã hết hàng hoặc không đủ tồn kho!");
        }

        variant.setAvailableStock(variant.getAvailableStock() - quantity);
        variant.setReservedStock(variant.getReservedStock() + quantity);
        productVariantRepository.save(variant);

        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(StockTransactionType.ORDER_RESERVE)
                .quantity(quantity)
                .note("Giữ chỗ cho đơn hàng mới")
                .build();
        stockTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void releaseStock(Long variantId, int quantity) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        int newReserved = Math.max(0, variant.getReservedStock() - quantity);
        variant.setReservedStock(newReserved);
        variant.setAvailableStock(variant.getAvailableStock() + quantity);
        productVariantRepository.save(variant);

        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(StockTransactionType.ORDER_RELEASE)
                .quantity(quantity)
                .note("Giải phóng kho tạm giữ")
                .build();
        stockTransactionRepository.save(transaction);
    }
}

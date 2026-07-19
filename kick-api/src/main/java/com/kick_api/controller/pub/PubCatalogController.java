package com.kick_api.controller.pub;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Brand;
import com.kick_api.entity.Category;
import com.kick_api.entity.Product;
import com.kick_api.service.BrandService;
import com.kick_api.service.CategoryService;
import com.kick_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pub/catalog")
@RequiredArgsConstructor
public class PubCatalogController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<Page<Product>>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/products/details/{slug}")
    public ResponseEntity<ApiResponse<Product>> getProductBySlug(@PathVariable String slug) {
        Product product = productService.getProductBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getActiveCategories() {
        List<Category> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<Brand>>> getActiveBrands() {
        List<Brand> brands = brandService.getActiveBrands();
        return ResponseEntity.ok(ApiResponse.success(brands));
    }
}

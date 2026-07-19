package com.kick_api.controller.admin;

import com.kick_api.dto.ApiResponse;
import com.kick_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/stock/update")
    public ResponseEntity<ApiResponse<Void>> updateStock(
            @RequestParam Long variantId,
            @RequestParam int quantityChange) {
        
        productService.updateStock(variantId, quantityChange);
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật số lượng tồn kho thành công!", null));
    }
}

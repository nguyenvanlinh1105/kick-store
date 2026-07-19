package com.kick_api.controller.admin;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Brand;
import com.kick_api.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Brand>> createBrand(@Valid @RequestBody Brand brand) {
        Brand created = brandService.createBrand(brand);
        return new ResponseEntity<>(ApiResponse.success("Tạo thương hiệu thành công!", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Brand>> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody Brand brandDetails) {
        Brand updated = brandService.updateBrand(id, brandDetails);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thương hiệu thành công!", updated));
    }
}

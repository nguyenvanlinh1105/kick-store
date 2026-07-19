package com.kick_api.controller.admin;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Category;
import com.kick_api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return new ResponseEntity<>(ApiResponse.success("Tạo danh mục thành công!", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category categoryDetails) {
        Category updated = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật danh mục thành công!", updated));
    }
}

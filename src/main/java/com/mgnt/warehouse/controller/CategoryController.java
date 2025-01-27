package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("list")
    @Operation(summary = "List all categories")
    public ResponseEntity<PagingResponse<Category>> getCategories(@RequestBody MetricSearch metricSearch) {
        log.info("list all category is call");
        return ResponseEntity.ok(categoryService.getCategories(metricSearch));
    }

    @PostMapping("create")
    @Operation(summary = "Create new category")
    public ResponseEntity<SuccessResponse> createCategory(@Valid @RequestBody Category category) {
        log.info("create new category is call");
        var categoryId = categoryService.createNewCategory(category);
        return ResponseEntity.ok(SuccessResponse.builder().message("Create Successful").data(categoryId).build());
    }

    @PutMapping("update")
    @Operation(summary = "Update existing category")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        log.info("update category is calling");
        return ResponseEntity.ok().body(
                SuccessResponse.builder().data(categoryService.updateCategory(category))
                        .message("Updated").build());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete existing category")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable("id") String id) {
        log.info("delete category with ID : {} is calling", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(SuccessResponse.builder().message("Deleted").build());
    }

    @GetMapping
    @Operation(summary = "Get category details")
    public ResponseEntity<Category> getCategory(@RequestParam("id") String id) {
        log.info("retrieve category by ID : {} is calling", id);
        return ResponseEntity.ok().body(categoryService.categoryDetails(id));
    }
}

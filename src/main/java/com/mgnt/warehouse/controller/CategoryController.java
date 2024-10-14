package com.mgnt.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.impl.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public ResponseEntity<Long> createCategory(@Valid @RequestBody Category category) {
        log.info("create new category is call");
        return ResponseEntity.ok(categoryService.createNewCategory(category));
    }

    @PutMapping("update")
    @Operation(summary = "Update existing category")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        log.info("update category is call");
        return ResponseEntity.ok().body(
                SuccessResponse.builder().data(categoryService.updateCategory(category))
                        .message("Updated").build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse> deleteCategory(@PathVariable("id") Long id) {
        log.info("delete category with ID : {} is call", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(SuccessResponse.builder().message("Deleted").build());
    }

    @GetMapping
    public ResponseEntity<Category> getCategory(@RequestParam("id") Long id) {
        log.info("retrive category by ID : {} is call", id);
        return ResponseEntity.ok().body(categoryService.categoryDetails(id));
    }
}

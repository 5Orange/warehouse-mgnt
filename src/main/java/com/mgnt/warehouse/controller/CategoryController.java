package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@RequiredArgsConstructor
public class CategoryController {

    private final IBaseService<Long, Category> categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> getCategories(@RequestParam(value = "name", required = false) String name) {
        return ResponseEntity.ok(categoryService.getAllWithFilter(name));
    }

    @PostMapping("create")
    public ResponseEntity<Long> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        categoryService.update(category);
        return ResponseEntity.ok().body(SuccessResponse.builder().message("Updated").build());
    }
}

package com.mgnt.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createProduct(@RequestBody CreateProductRequest productRequest) {
        Long did = productService.createProduct(productRequest);
        return ResponseEntity.ok().body(SuccessResponse.success("Product is created", did));
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(SuccessResponse.success(productService.getProductById(id)));
    }

    @GetMapping("list")
    public ResponseEntity<SuccessResponse> getProducts() {
        return ResponseEntity.ok().body(SuccessResponse.success(productService.getProducts()));
    }
}

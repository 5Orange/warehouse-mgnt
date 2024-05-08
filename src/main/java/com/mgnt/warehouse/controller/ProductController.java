package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

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

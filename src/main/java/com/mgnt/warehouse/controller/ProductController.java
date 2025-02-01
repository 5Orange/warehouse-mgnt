package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.modal.request.ImportProductEntity;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("create")
    @Operation(summary = "create new product")
    public ResponseEntity<SuccessResponse> createProduct(@RequestBody CreateProductRequest productRequest) {
        log.info("Creating new product");
        String did = productService.createProduct(productRequest);
        return ResponseEntity.ok().body(SuccessResponse.success("Product is created", did));
    }

    @GetMapping("{id}")
    @Operation(summary = "Get specific product by id")
    public ResponseEntity<SuccessResponse> getProduct(@PathVariable("id") String id) {
        log.info("Get product by id is calling");
        return ResponseEntity.ok().body(SuccessResponse.success(productService.getProductById(id)));
    }

    @PostMapping("list")
    @Operation(summary = "list all products with filter")
    public ResponseEntity<SuccessResponse> getProducts(@RequestBody MetricSearch metricSearch) {
        log.info("list all products is calling");
        return ResponseEntity.ok().body(SuccessResponse.success(productService.getProducts(metricSearch)));
    }

    @PostMapping("import")
    public ResponseEntity<SuccessResponse> imports(@RequestBody @NotEmpty @Valid List<ImportProductEntity> importProducts) {
        productService.importProduct(importProducts);
        return ResponseEntity.accepted().body(SuccessResponse.success("Received"));
    }
}

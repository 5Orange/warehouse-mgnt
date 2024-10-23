package com.mgnt.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createSupplier(@RequestBody Supplier supplier) {
        String id = supplierService.createSupplier(supplier);
        return ResponseEntity.ok().body(SuccessResponse.builder().message("Create Successful").data(id).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getSupplier(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(SuccessResponse.success(supplierService.getSupplierById(id)));
    }

    @GetMapping("list")
    public ResponseEntity<SuccessResponse> getSuppliers(
            @RequestParam(value = "filter", required = false) String filter) {
        return ResponseEntity.ok().body(SuccessResponse.success(supplierService.getAllSupplier()));
    }
}

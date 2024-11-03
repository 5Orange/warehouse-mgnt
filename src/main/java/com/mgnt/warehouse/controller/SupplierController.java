package com.mgnt.warehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("update")
    public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier, @RequestParam("id") String id) {
        supplierService.updateSuplier(supplier, id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("list")
    public ResponseEntity<SuccessResponse> getSuppliers(@RequestBody MetricSearch metricSearch) {
        return ResponseEntity.ok().body(SuccessResponse.success(supplierService.getAllSupplier(metricSearch)));
    }
}

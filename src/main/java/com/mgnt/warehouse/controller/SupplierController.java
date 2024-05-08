package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createSupplier(@RequestBody Supplier supplier) {
        Long id = supplierService.createSupplier(supplier);
        return ResponseEntity.ok().body(SuccessResponse.builder().message("Create Successful").data(id).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getSupplier(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(SuccessResponse.success(supplierService.getSupplierById(id)));
    }

    @GetMapping("list")
    public ResponseEntity<SuccessResponse> getSuppliers(@RequestParam(value = "filter", required = false) String filter) {
        return ResponseEntity.ok().body(SuccessResponse.success(supplierService.getAllSupplier()));
    }
}

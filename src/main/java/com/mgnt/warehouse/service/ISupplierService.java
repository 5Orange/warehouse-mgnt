package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {
    Optional<Supplier> getSupplierById(Long id);

    Long createSupplier(Supplier supplier);

    List<Supplier> getAllSupplier();
}

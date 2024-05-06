package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.repository.SupplierRepository;
import com.mgnt.warehouse.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements ISupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Id can not be null!");
        }
        return supplierRepository.findById(id);
    }

    @Override
    public Long createSupplier(Supplier supplier) {
        if (StringUtils.isEmpty(supplier.getName())) {
            throw new InvalidRequestException("Supplier name is required");
        }

        if (supplierRepository.existsByName(supplier.getName())) {
            throw new DuplicateException("Supplier is already existing!");
        }
        return supplierRepository.save(supplier).getId();
    }
}

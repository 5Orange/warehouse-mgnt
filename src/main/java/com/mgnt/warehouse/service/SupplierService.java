package com.mgnt.warehouse.service;

import static com.mgnt.warehouse.utils.ServiceUtils.generateSupplierCode;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Optional<Supplier> getSupplierById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Id can not be null!");
        }
        return supplierRepository.findById(id);
    }

    public Long createSupplier(Supplier supplier) {
        if (StringUtils.isEmpty(supplier.getName())) {
            throw new InvalidRequestException("Supplier name is required");
        }

        if (supplierRepository.existsByName(supplier.getName())) {
            throw new DuplicateException("Supplier is already existing!");
        }
        supplier.setCode(generateSupplierCode());
        return supplierRepository.save(supplier).getId();
    }

    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }
}

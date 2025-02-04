package com.mgnt.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.common.MetricFilter;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.enums.Action;
import com.mgnt.warehouse.modal.enums.TraceItem;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.SupplierMapper;
import com.mgnt.warehouse.modal.predicate.SupplierPredicate;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.repository.SupplierRepository;
import com.mgnt.warehouse.utils.ApplicationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.mgnt.warehouse.utils.ServiceUtils.generateSupplierCode;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final TracingService tracingService;
    private final ObjectMapper objectMapper;

    public Supplier getSupplierById(String id) {
        if (id == null) {
            throw new InvalidRequestException("Id can not be null!");
        }
        return supplierRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("No Supplier found"));
    }

    public String createSupplier(Supplier supplier) {
        if (StringUtils.isEmpty(supplier.getName())) {
            throw new InvalidRequestException("Supplier name is required");
        }

        if (supplierRepository.existsByName(supplier.getName())) {
            throw new DuplicateException("Supplier is already existing!");
        }
        supplier.setSupplierCode(generateSupplierCode());
        var id = supplierRepository.save(supplier).getId();
        tracingService.save(Action.CREATE, TraceItem.SUPPLIER, "Create new supplier: " + id);
        return id;
    }

    public PagingResponse<Supplier> getAllSupplier(MetricSearch metricSearch) {
        SupplierPredicate.SupplierPredicateBuilder supplierFilter = SupplierPredicate.builder();

        if (!CollectionUtils.isEmpty(metricSearch.metricFilters())) {
            for (MetricFilter filters : metricSearch.metricFilters()) {
                String value = filters.value();
                switch (filters.filterField()) {
                    case "name" -> supplierFilter.supplierNameLike(value);
                    case "phone" -> supplierFilter.supplierPhoneLike(value);
                    case "address" -> supplierFilter.addressLike(value);
                    case "code" -> supplierFilter.codeLike(value);
                    case "createDate" -> supplierFilter.createDateBetween(filters.from(), filters.to());
                }
            }
        }
        Pageable pageable = ApplicationUtils.getPageable(metricSearch);
        Page<Supplier> products = supplierRepository.findAll(supplierFilter.build(), pageable);
        return new PagingResponse<>(products);
    }

    @Transactional
    public void updateSupplier(Supplier supplier, String id) {
        var existsSupplier = this.getSupplierById(id);
        tracingService.save(
                Action.UPDATE,
                TraceItem.SUPPLIER,
                String.format("supplier was changed fom : %s to %s", objectMapper.valueToTree(existsSupplier), objectMapper.valueToTree(supplier)));
        supplierMapper.update(existsSupplier, supplier);
        supplierRepository.save(existsSupplier);
    }
}

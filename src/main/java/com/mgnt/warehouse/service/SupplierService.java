package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.common.MetricFilter;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.SupplierMapper;
import com.mgnt.warehouse.modal.predicate.SupplierPredicate;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.repository.SupplierRepository;
import com.mgnt.warehouse.utils.ApplicationUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
        supplier.setCode(generateSupplierCode());
        return supplierRepository.save(supplier).getId();
    }

    public PagingResponse<Supplier> getAllSupplier(MetricSearch metricSearch) {
        BooleanExpression supplierFilter = Expressions.asBoolean(true).isTrue();

        if (!CollectionUtils.isEmpty(metricSearch.getMetricFilters())) {
            for (MetricFilter filters : metricSearch.getMetricFilters()) {
                String value = filters.getValue();
                supplierFilter = switch (filters.getFilterField()) {
                    case "name" -> SupplierPredicate.supplierNameLike(supplierFilter, value);
                    case "phone" -> SupplierPredicate.supplierPhoneLike(supplierFilter, value);
                    case "address" -> SupplierPredicate.addressLike(supplierFilter, value);
                    case "code" -> SupplierPredicate.codeLike(supplierFilter, value);
                    default -> supplierFilter;
                };
            }
        }
        Pageable pageable = ApplicationUtils.getPageable(metricSearch);
        Page<Supplier> products = supplierRepository.findAll(supplierFilter, pageable);
        return new PagingResponse<>(products);
    }

    @Transactional
    public void updateSuplier(Supplier supplier, String id) {
        var existsSuplier = this.getSupplierById(id);
        supplierMapper.update(existsSuplier, supplier);
        supplierRepository.save(existsSuplier);
    }
}

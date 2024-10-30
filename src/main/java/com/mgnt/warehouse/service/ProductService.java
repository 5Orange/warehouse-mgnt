package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.Product;
import com.mgnt.warehouse.modal.Quantity;
import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.common.MetricFilter;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.exception.NotFoundException;
import com.mgnt.warehouse.modal.predicate.ProductPredicate;
import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.repository.ProductRepository;
import com.mgnt.warehouse.repository.QuantityRepository;
import com.mgnt.warehouse.utils.ApplicationUtils;
import com.mgnt.warehouse.utils.ServiceUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;

    @Transactional
    public String createProduct(CreateProductRequest createProductRequest) {
        Optional<Supplier> supplier = supplierService.getSupplierById(createProductRequest.getSupplierId());
        if (supplier.isEmpty()) {
            throw new InvalidRequestException("Supplier can not be null!");
        }

        Category category = categoryService.categoryDetails(createProductRequest.getCategoryId());

        Product product = Product.builder()
            .productCode(ServiceUtils.generateProductId())
            .name(createProductRequest.getName())
            .price(createProductRequest.getPrice())
            .category(category)
            .supplier(supplier.get())
            .build();

        Quantity quantity = Quantity.builder()
            .value(createProductRequest.getQuantity())
            .build();

        quantityRepository.save(quantity);
        quantity.setProduct(product);
        product.setQuantity(quantity);

        return productRepository.save(product).getId();
    }

    @SneakyThrows
    public Product getProductById(Long id) {
        if (id == null) {
            throw new BadRequestException("id can not be null!");
        }
        return productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    public PagingResponse<Product> getProducts(MetricSearch metricSearch) {
        BooleanExpression productFilter = Expressions.asBoolean(true).isTrue();

        if (!CollectionUtils.isEmpty(metricSearch.getMetricFilters())) {
            for (MetricFilter filters : metricSearch.getMetricFilters()) {
                String value = filters.getValue();
                productFilter = switch (filters.getFilterField()) {
                    case "name" -> ProductPredicate.productNameLike(productFilter, value);
                    case "productCode" -> ProductPredicate.productCodeLike(productFilter, value);
                    case "category" -> ProductPredicate.categoryLike(productFilter, value);
                    case "supplier" -> ProductPredicate.supplierNameLike(productFilter, value);
                    default -> productFilter;
                };
            }
        }
        Pageable pageable = ApplicationUtils.getPageable(metricSearch);
        Page<Product> products = productRepository.findAll(productFilter, pageable);
        return new PagingResponse<>(products);
    }
}

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
import com.mgnt.warehouse.modal.request.ImportProductEntity;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.repository.ProductRepository;
import com.mgnt.warehouse.repository.QuantityRepository;
import com.mgnt.warehouse.utils.Action;
import com.mgnt.warehouse.utils.ApplicationUtils;
import com.mgnt.warehouse.utils.ServiceUtils;
import com.mgnt.warehouse.utils.TraceItem;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final TracingService tracingService;

    @Transactional
    public String createProduct(CreateProductRequest createProductRequest) {
        Supplier supplier = supplierService.getSupplierById(createProductRequest.supplierId());

        Category category = categoryService.categoryDetails(createProductRequest.categoryId());

        Product product = Product.builder()
                .productCode(ServiceUtils.generateProductId())
                .name(createProductRequest.name())
                .price(createProductRequest.price())
                .category(category)
                .supplier(supplier)
                .build();

        Quantity quantity = Quantity.builder()
                .value(createProductRequest.quantity())
                .build();

        quantityRepository.save(quantity);
        quantity.setProduct(product);
        product.setQuantity(quantity);

        var id = productRepository.save(product).getId();
        tracingService.save(Action.CREATE, TraceItem.PRODUCT, "Create new product: " + id);
        return id;
    }

    @Transactional
    public void importProduct(List<ImportProductEntity> importProductEntityList) {
        importProductEntityList.forEach(item -> {
            Iterable<Product> productIterable = productRepository.findAll(ProductPredicate.findByProductCodeAndCategoryCodeAndSupplierCode(item.productCode(), item.supplierCode(), item.categoryCode()));
            if (!productIterable.iterator().hasNext()) {
                throw new InvalidRequestException("Invalid product");
            }
            productIterable.forEach(product -> {
                product.getQuantity().setValue(item.quantity() + product.getQuantity().getValue());
                tracingService.save(Action.UPDATE,
                        TraceItem.PRODUCT,
                        String.format("%s %s increased by %s unit(s)", product.getProductCode(), product.getName(), item.quantity()));
                productRepository.save(product);
            });
        });
    }

    @SneakyThrows
    public Product getProductById(String id) {
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

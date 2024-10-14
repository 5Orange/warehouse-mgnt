package com.mgnt.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.Product;
import com.mgnt.warehouse.modal.Quantity;
import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.exception.NotFoundException;
import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.repository.ProductRepository;
import com.mgnt.warehouse.repository.QuantityRepository;
import com.mgnt.warehouse.utils.ServiceUtils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;

    public Long createProduct(CreateProductRequest createProductRequest) {
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

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}

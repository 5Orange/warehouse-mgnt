package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.Product;
import com.mgnt.warehouse.modal.Quantity;
import com.mgnt.warehouse.modal.Supplier;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.exception.NotFoundException;
import com.mgnt.warehouse.modal.request.CreateProductRequest;
import com.mgnt.warehouse.repository.ProductRepository;
import com.mgnt.warehouse.repository.QuantityRepository;
import com.mgnt.warehouse.service.IBaseService;
import com.mgnt.warehouse.service.IProductService;
import com.mgnt.warehouse.service.ISupplierService;
import com.mgnt.warehouse.service.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final ISupplierService supplierService;
    private final IBaseService<Long, Category> categoryService;

    @Override
    public Long createProduct(CreateProductRequest createProductRequest) {
        Optional<Supplier> supplier = supplierService.getSupplierById(createProductRequest.getSupplierId());
        if (supplier.isEmpty()) {
            throw new InvalidRequestException("Supplier can not be null!");
        }

        Optional<Category> category = categoryService.getById(createProductRequest.getCategoryId());
        if (category.isEmpty()) {
            throw new InvalidRequestException("Category can not be null!");
        }
        Product product = Product.builder()
                .productCode(ServiceUtils.generateProductId())
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .category(category.get())
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

    @Override
    @SneakyThrows
    public Product getProductById(Long id) {
        if (id == null) {
            throw new BadRequestException("id can not be null!");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}

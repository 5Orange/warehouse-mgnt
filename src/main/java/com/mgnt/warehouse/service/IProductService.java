package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Product;
import com.mgnt.warehouse.modal.request.CreateProductRequest;

import java.util.List;

public interface IProductService {
    Long createProduct(CreateProductRequest product);

    Product getProductById(Long id);

    List<Product> getProducts();
}

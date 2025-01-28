package com.mgnt.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgnt.warehouse.modal.OrderEntity;
import com.mgnt.warehouse.modal.Product;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.OrderRequestMapper;
import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import com.mgnt.warehouse.modal.request.OrderProduct;
import com.mgnt.warehouse.repository.OrderRepository;
import com.mgnt.warehouse.repository.ProductRepository;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mgnt.warehouse.utils.ServiceUtils.generateOrderCode;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderRequestMapper orderRequestMapper;
    private final ObjectMapper objectMapper;

    public String createOrder(CreateOrderRequest request) {
        var productIds = request.getOrderProducts()
                .stream().map(OrderProduct::productId)
                .toList();
        var products = productRepository.findAllByIdIn(productIds);

        if (Collections.isEmpty(products)) {
            throw new InvalidRequestException("Product not found");
        }

        List<Product> productList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        request.getOrderProducts()
                .forEach(product -> {
                    var productDb = products.stream()
                            .filter(p -> product.productId().equals(p.getId()))
                            .findFirst()
                            .orElse(null);
                    if (productDb == null) {
                        throw new InvalidRequestException(product.productId() + ": not found");
                    }
                    if (productDb.getQuantity().getValue() - product.quantity() < 0) {
                        throw new InvalidRequestException(product.productId() + ": out of stock");
                    }
                    productDb.getQuantity().setValue(productDb.getQuantity().getValue() - product.quantity());
                    productList.add(productDb);
                });
        OrderEntity orderEntity = orderRequestMapper.toOrder(request);
        orderEntity.setOrderId(generateOrderCode());
        orderEntity.setProducts(productList);
        orderEntity.setOrderProducts(objectMapper.valueToTree(request.getOrderProducts()));
        return orderRepository.save(orderEntity).getOrderId();
    }

    public OrderEntity getOrderById(String orderId) {
        return ofNullable(orderId)
                .flatMap(orderRepository::findOrderEntityByOrderId)
                .orElseThrow(InvalidRequestException::new);
    }
}

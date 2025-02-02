package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.OrderItem;
import com.mgnt.warehouse.modal.Orders;
import com.mgnt.warehouse.modal.enums.OrderStatus;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.OrderRequestMapper;
import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import com.mgnt.warehouse.modal.request.OrderProduct;
import com.mgnt.warehouse.repository.OrderRepository;
import com.mgnt.warehouse.repository.ProductRepository;
import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mgnt.warehouse.modal.enums.Action.CREATE;
import static com.mgnt.warehouse.modal.enums.TraceItem.ORDER;
import static com.mgnt.warehouse.utils.ServiceUtils.generateOrderCode;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderRequestMapper orderRequestMapper;
    private final TracingService tracingService;

    @Transactional
    public String createOrder(CreateOrderRequest request) {
        var productIds = request.getOrderProducts()
                .stream().map(OrderProduct::productId)
                .toList();
        var products = productRepository.findAllByIdIn(productIds);

        if (Collections.isEmpty(products)) {
            throw new InvalidRequestException("Product not found");
        }

        Orders order = Orders.builder()
                .orderId(generateOrderCode())
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerName())
                .customerAddress(request.getDeliveryAddress())
                .status(OrderStatus.CREATED.name())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderProduct orderProduct : request.getOrderProducts()) {
            var productDb = products.stream()
                    .filter(p -> orderProduct.productId().equals(p.getId()))
                    .findFirst()
                    .orElse(null);
            if (productDb == null) {
                throw new InvalidRequestException(orderProduct.productId() + ": not found");
            }
            if (productDb.getStockQuantity() - orderProduct.quantity() < 0) {
                throw new InvalidRequestException(orderProduct.productId() + ": out of stock");
            }
            var orderItem = orderRequestMapper.toOrderItem(orderProduct);
            orderItem.setProduct(productDb);
            orderItem.setOrders(order);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(productDb.getPrice().multiply(BigDecimal.valueOf(orderProduct.quantity())));
            productDb.setStockQuantity(productDb.getStockQuantity() - orderProduct.quantity());
        }
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        var orderId = orderRepository.save(order).getOrderId();
        tracingService.save(CREATE, ORDER, "Create new order: " + orderId);
        return orderId;
    }

    public Orders getOrderById(String orderId) {
        return ofNullable(orderId)
                .flatMap(orderRepository::findOrderEntityByOrderId)
                .orElseThrow(InvalidRequestException::new);
    }
}

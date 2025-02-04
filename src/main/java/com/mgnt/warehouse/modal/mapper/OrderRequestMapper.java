package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.OrderItem;
import com.mgnt.warehouse.modal.request.OrderProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {
    OrderItem toOrderItem(OrderProduct orderProduct);

    List<OrderItem> toOrderItems(List<OrderProduct> orderProducts);
}

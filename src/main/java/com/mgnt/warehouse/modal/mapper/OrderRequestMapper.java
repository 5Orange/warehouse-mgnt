package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.OrderEntity;
import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {
    OrderEntity toOrder(CreateOrderRequest createOrderRequest);
}

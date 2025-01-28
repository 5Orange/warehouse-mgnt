package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.OrderEntity;
import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {
    @Mapping(target = "orderProducts", ignore = true)
    OrderEntity toOrder(CreateOrderRequest createOrderRequest);
}

package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.OrderItem;
import com.mgnt.warehouse.modal.request.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toOrderItem(OrderProduct orderProduct);

    List<OrderItem> toOrderItems(List<OrderProduct> orderProducts);
}

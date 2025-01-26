package com.mgnt.warehouse.modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderEntity extends BaseEntity{
    private String orderId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_product",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}

package com.mgnt.warehouse.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
public class Product extends BaseEntity {
    private String name;

    private String productCode;

    private BigDecimal price;

    @OneToOne
    private Quantity quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;


}

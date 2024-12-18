package com.mgnt.warehouse.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(exclude = {"quantity", "category", "supplier"}, callSuper = false)
@Entity
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Product extends BaseEntity {
    private String name;

    private String productCode;

    private BigDecimal price;

    @OneToOne(mappedBy = "product",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private Quantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Supplier supplier;

}

package com.mgnt.warehouse.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Quantity extends BaseEntity {

    @OneToOne
    private Product product;

    private Long count;
}

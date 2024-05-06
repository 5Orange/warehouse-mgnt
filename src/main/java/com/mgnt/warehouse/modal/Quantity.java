package com.mgnt.warehouse.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Quantity extends BaseEntity {

    @OneToOne
    @JsonBackReference
    private Product product;

    private Long count;
}

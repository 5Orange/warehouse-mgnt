package com.mgnt.warehouse.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Category extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}

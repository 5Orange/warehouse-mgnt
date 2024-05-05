package com.mgnt.warehouse.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Provider extends BaseEntity {
    private String name;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "provider")
    private List<Product> products;
}

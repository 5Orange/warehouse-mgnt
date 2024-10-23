package com.mgnt.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgnt.warehouse.modal.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    boolean existsByName(String name);
}

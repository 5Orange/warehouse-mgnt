package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, QuerydslPredicateExecutor<Supplier> {

    boolean existsByName(String name);
}

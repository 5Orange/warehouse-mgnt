package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, QuerydslPredicateExecutor<Product> {
    List<Product> findAllByIdIn(List<String> ids);
}

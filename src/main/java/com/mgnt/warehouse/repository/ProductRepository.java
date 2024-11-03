package com.mgnt.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.mgnt.warehouse.modal.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, QuerydslPredicateExecutor<Product> {
}

package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String>, QuerydslPredicateExecutor<Orders> {
    Optional<Orders> findOrderEntityByOrderId(String orderId);
}

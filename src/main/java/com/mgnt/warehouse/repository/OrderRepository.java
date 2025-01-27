package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    Optional<OrderEntity> findOrderEntityByOrderId(String orderId);
}

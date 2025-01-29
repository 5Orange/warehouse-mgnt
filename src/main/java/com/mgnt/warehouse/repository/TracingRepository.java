package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.TracingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracingRepository extends JpaRepository<TracingEntity, String> {
}

package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.TracingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TracingRepository extends JpaRepository<TracingEntity, String> {
    @Query(nativeQuery = true, value = "select * from tracing_entity where trace_item = :name and create_date > (CURRENT_DATE - 30)")
    List<TracingEntity> findHistoryLast30sDay(@Param("name") String name);
}

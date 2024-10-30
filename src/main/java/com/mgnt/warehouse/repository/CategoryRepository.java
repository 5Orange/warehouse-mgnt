package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {

    List<Category> findAllByNameIsContaining(String name);

    boolean existsCategoryByName(String name);

    Optional<Category> findCategoryById(String id);

}

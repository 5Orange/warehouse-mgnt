package com.mgnt.warehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.mgnt.warehouse.modal.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    List<Category> findAllByNameIsContaining(String name);

    boolean existsCategoryByName(String name);

    Optional<Category> findCategoryById(Long id);

}

package com.mgnt.warehouse.repository;

import com.mgnt.warehouse.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByNameIsContaining(String name);

    boolean existsCategoryByName(String name);

    Category findCategoryById(Long id);

}

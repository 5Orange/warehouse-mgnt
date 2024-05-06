package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<Category> getCategories(String name);

    Long addCategory(Category category);

    void update(Category category);
    
    Optional<Category> getCategoryById(Long id);

}

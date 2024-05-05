package com.mgnt.warehouse.service;

import com.mgnt.warehouse.modal.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getCategories(String name);

    Long addCategory(Category category);

    void update(Category category);

}

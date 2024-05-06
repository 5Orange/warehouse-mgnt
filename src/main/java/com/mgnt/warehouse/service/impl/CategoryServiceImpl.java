package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.CategoryMapper;
import com.mgnt.warehouse.repository.CategoryRepository;
import com.mgnt.warehouse.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategories(String name) {
        return categoryRepository.findAllByNameIsContaining(name);
    }

    @Override
    public Long addCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new DuplicateException();
        }
        return categoryRepository.save(category).getId();
    }

    @Override
    public void update(Category category) {
        categoryRepository.findCategoryById(category.getId())
                .ifPresent(c -> {
                    Category target = categoryMapper.toCategory(category);
                    target.setId(c.getId());
                    target.setCreateDate(c.getCreateDate());
                    target.setCreatedBy(c.getCreatedBy());
                    categoryRepository.save(target);
                });
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Id can not be null!");
        }
        return categoryRepository.findCategoryById(id);
    }
}

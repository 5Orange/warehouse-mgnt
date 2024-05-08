package com.mgnt.warehouse.service.impl;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.CategoryMapper;
import com.mgnt.warehouse.repository.CategoryRepository;
import com.mgnt.warehouse.service.IBaseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mgnt.warehouse.service.ServiceUtils.generateCategoryCode;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements IBaseService<Long, Category> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllWithFilter(String name) {
        return ofNullable(name)
                .filter(StringUtils::isNotEmpty)
                .map(categoryRepository::findAllByNameIsContaining)
                .orElseGet(categoryRepository::findAll);
    }

    @Override
    public Long create(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new DuplicateException();
        }
        category.setCategoryCode(generateCategoryCode());
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
    public Optional<Category> getById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Id can not be null!");
        }
        return categoryRepository.findCategoryById(id);
    }
}

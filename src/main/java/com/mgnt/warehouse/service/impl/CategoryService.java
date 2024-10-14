package com.mgnt.warehouse.service.impl;

import static com.mgnt.warehouse.utils.ServiceUtils.generateCategoryCode;
import static java.util.Optional.ofNullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mgnt.warehouse.modal.Category;
import com.mgnt.warehouse.modal.common.MetricFilter;
import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.modal.mapper.CategoryMapper;
import com.mgnt.warehouse.modal.predicate.CategoryPredicate;
import com.mgnt.warehouse.modal.response.PagingResponse;
import com.mgnt.warehouse.repository.CategoryRepository;
import com.mgnt.warehouse.utils.ApplicationUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public PagingResponse<Category> getCategories(MetricSearch metricSearch) {
        return ofNullable(metricSearch)
                .map(metrics -> {
                    BooleanExpression bExpression = Expressions.asBoolean(true).isTrue();
                    if (!CollectionUtils.isEmpty(metrics.getMetricFilters())) {
                        for (MetricFilter aaa : metrics.getMetricFilters()) {
                            if ("name".equals(aaa.getFilterField())) {
                                bExpression = CategoryPredicate.categoryNameLike(bExpression, aaa.getValue());
                            }
                            if ("categoryCode".equals(aaa.getFilterField())) {
                                bExpression = CategoryPredicate.codeLike(bExpression, aaa.getValue());
                            }
                        }
                    }

                    Pageable pageable = ApplicationUtils.getPageable(metricSearch);
                    Page<Category> result = categoryRepository.findAll(bExpression, pageable);
                    return PagingResponse.<Category>builder()
                            .data(result.getContent())
                            .totalPage(result.getTotalPages())
                            .totalItems(result.getTotalElements())
                            .hasNext(result.hasNext())
                            .build();
                }).orElse(null);
    }

    public Category categoryDetails(Long id) {
        return ofNullable(id)
                .flatMap(i -> categoryRepository.findCategoryById(id))
                .orElseThrow(() -> new InvalidRequestException("Category not found"));
    }

    public Long createNewCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new DuplicateException();
        }
        category.setCategoryCode(generateCategoryCode());
        return categoryRepository.save(category).getId();
    }

    public Long updateCategory(Category category) {
        return categoryRepository.findCategoryById(category.getId())
                .map(c -> {
                    Category target = categoryMapper.toCategory(category);
                    target.setId(c.getId());
                    target.setCreateDate(c.getCreateDate());
                    target.setCreatedBy(c.getCreatedBy());
                    return categoryRepository.save(target).getId();
                }).orElseThrow(() -> new InvalidRequestException("Category not found"));
    }

    public void deleteCategory(Long id) {
        ofNullable(id).ifPresent(i -> categoryRepository.deleteById(i));
    }
}

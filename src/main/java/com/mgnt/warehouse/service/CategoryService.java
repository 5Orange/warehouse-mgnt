package com.mgnt.warehouse.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.mgnt.warehouse.modal.enums.Action.CREATE;
import static com.mgnt.warehouse.modal.enums.Action.UPDATE;
import static com.mgnt.warehouse.modal.enums.TraceItem.CATEGORY;
import static com.mgnt.warehouse.utils.ServiceUtils.generateCategoryCode;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final TracingService tracingService;

    public PagingResponse<Category> getCategories(MetricSearch metricSearch) {
        return ofNullable(metricSearch)
                .map(metrics -> {
                    CategoryPredicate.CategoryPredicateBuilder categoryPredicateBuilder = CategoryPredicate.builder();
                    if (metricSearch.metricFilters() != null) {
                        for (MetricFilter filters : metricSearch.metricFilters()) {
                            String value = filters.value();
                            switch (filters.filterField()) {
                                case "name" -> categoryPredicateBuilder.categoryNameLike(value);
                                case "categoryCode" -> categoryPredicateBuilder.codeLike(value);
                                case "createDate" ->
                                        categoryPredicateBuilder.createDateBetween(filters.from(), filters.to());
                            }
                        }
                    }

                    Pageable pageable = ApplicationUtils.getPageable(metricSearch);
                    Page<Category> result = categoryRepository.findAll(categoryPredicateBuilder.build(), pageable);
                    return new PagingResponse<>(result);
                }).orElse(null);
    }

    public Category categoryDetails(String id) {
        return ofNullable(id)
                .flatMap(i -> categoryRepository.findCategoryById(id))
                .orElseThrow(() -> new InvalidRequestException("Category not found"));
    }

    public String createNewCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new DuplicateException();
        }
        category.setCategoryCode(generateCategoryCode());

        var categoryId = categoryRepository.save(category).getId();
        tracingService.save(CREATE, CATEGORY, "Adding new category: " + categoryId);
        return categoryId;
    }

    public String updateCategory(Category category) {
        return categoryRepository.findCategoryById(category.getId())
                .map(c -> {
                    tracingService.save(UPDATE, CATEGORY, "Update the category: " + c.getCategoryCode());
                    Category target = categoryMapper.toCategory(category);
                    target.setId(c.getId());
                    target.setCreateDate(c.getCreateDate());
                    target.setCreatedBy(c.getCreatedBy());
                    return categoryRepository.save(target).getId();
                }).orElseThrow(() -> new InvalidRequestException("Category not found"));
    }

    public void deleteCategory(String id) {
        // need to check if any product available
        ofNullable(id).ifPresent(categoryRepository::deleteById);
    }
}

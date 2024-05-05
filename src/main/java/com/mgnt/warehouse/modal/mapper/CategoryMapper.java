package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(Category category);
}

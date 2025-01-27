package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class SupplierMapper {

    public abstract void update(@MappingTarget Supplier target, Supplier source);

}

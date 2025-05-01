package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.UserDto;
import com.mgnt.warehouse.modal.auth.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}

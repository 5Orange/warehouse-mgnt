package com.mgnt.warehouse.modal.mapper;

import com.mgnt.warehouse.modal.auth.User;
import com.mgnt.warehouse.modal.request.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SigninRequestMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(SignUpRequest signUpRequest);
}

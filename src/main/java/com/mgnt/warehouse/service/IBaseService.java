package com.mgnt.warehouse.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<K, T> {
    List<T> getAllWithFilter(String filter);

    K create(T t);

    void update(T t);

    Optional<T> getById(K id);

}

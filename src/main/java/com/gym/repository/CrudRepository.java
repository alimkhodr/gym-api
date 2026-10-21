package com.gym.repository;

import com.gym.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends BaseEntity> {

    List<T> findAll();

    Optional<T> findById(long id);

    T save(T entity);

    boolean deleteById(long id);
}

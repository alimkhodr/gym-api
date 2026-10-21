package com.gym.service;

import com.gym.exception.BadRequestException;
import com.gym.exception.NotFoundException;
import com.gym.model.BaseEntity;
import com.gym.repository.CrudRepository;

import java.util.List;

public class CrudService<T extends BaseEntity> {

    private final CrudRepository<T> repository;
    private final String name;

    public CrudService(CrudRepository<T> repository, String name) {
        this.repository = repository;
        this.name = name;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(name + " " + id + " not found"));
    }

    public T create(T entity) {
        entity.setId(null);
        return persist(entity);
    }

    public T update(long id, T entity) {
        findById(id);
        entity.setId(id);
        return persist(entity);
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException(name + " " + id + " not found");
        }
    }

    public void checkExists(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BadRequestException(name + " " + id + " does not exist");
        }
    }

    protected void checkReferences(T entity) {
    }

    private T persist(T entity) {
        entity.validate();
        checkReferences(entity);
        return repository.save(entity);
    }
}

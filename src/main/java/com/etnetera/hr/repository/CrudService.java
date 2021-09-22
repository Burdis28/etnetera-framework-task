package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    public Optional<T> get(ID id) {
        return getRepository().findById(id);
    }

    public T update(T entity) {
        return getRepository().save(entity);
    }

    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    public Iterable<T> listAll() {
        return getRepository().findAll();
    }

    public int count() {
        return (int) getRepository().count();
    }

}

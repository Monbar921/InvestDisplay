package ru.invest.display.dao;

import ru.invest.display.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {
    E save(E entity);

    void delete(K id);

    void update(E entity);

    Optional<E> merge(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    List<E> findByArguments(String name, Map<String, Object> arguments);
}
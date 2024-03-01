package Pegas.dao;

import Pegas.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable,T extends BaseEntity<K>> {
    T save(T entity);
    void delete(K id);
    void update(T entity);
    Optional<T> findBuId(K id);
    List<T> findAll();
}

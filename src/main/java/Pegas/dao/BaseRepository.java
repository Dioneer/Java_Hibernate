package Pegas.dao;

import Pegas.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, T extends BaseEntity<K>> implements Repository<K, T>{
    @Getter
    private final EntityManager entityManager;
    private final Class<T> clazz;
    @Override
    public T save(T entity) {
        entityManager.persist(entity);
            return entity;
    }

    @Override
    public void delete(K id) {
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<T> findBuId(K id) {
            return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<T> findAll() {
            var creteria = entityManager.getCriteriaBuilder().createQuery(clazz);
            creteria.from(clazz);
            return entityManager.createQuery(creteria).getResultList();
    }
}

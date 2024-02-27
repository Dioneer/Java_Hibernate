package Pegas.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public interface BaseEntity<T extends Serializable> {
    T getId();
    void setId(T id);
}

package com.bintime.services;

import java.util.List;

/**
 * <p>Base Entity Service interface</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 */
public interface EntityService<T> {
    T find(Long id);
    void add(T entity);
    T update(T entity);
    void remove(Long id);
    List<T> list();
}

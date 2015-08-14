package com.bintime.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>Base db logic interface</p>
 * <p>Data Access Object</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 */
public interface Dao<T> {
    T find(Long id);
    void add(T entity);
    T update(T entity);
    void remove(T entity);
    long count();
    List<T> list();
    List<T> list(String orderBy);
    List<T> list(int start, int limit, String orderBy, String sortOrder);
    List<T> list(int start, int limit, String orderBy, Map<String, Object> where);
    List<T> list(int start, int limit, String orderBy, String sortOrder, Map<String, Object> where);
}

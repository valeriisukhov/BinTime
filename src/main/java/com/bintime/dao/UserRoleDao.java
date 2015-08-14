package com.bintime.dao;

import com.bintime.entity.UserRole;

/**
 * <p>User Role db logic</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserRole
 * @see    Dao
 */
public interface UserRoleDao extends Dao<UserRole>{
    UserRole findByName(String name);
}

package com.bintime.services;

import com.bintime.entity.UserRole;

/**
 * <p>User Role Service interface</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserRole
 * @see    EntityService
 */
public interface UserRoleService extends EntityService<UserRole>{
    UserRole findByName(String name);
}

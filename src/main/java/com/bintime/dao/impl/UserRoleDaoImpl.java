package com.bintime.dao.impl;

import com.bintime.dao.UserRoleDao;
import com.bintime.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <p>User Role db logic implementation</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserRole
 * @see    UserRoleDao
 * @see    DaoImpl
 */
@Repository
public class UserRoleDaoImpl extends DaoImpl<UserRole> implements UserRoleDao {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    public UserRoleDaoImpl() {
        super(UserRole.class);
    }

    /**
     * Find role by role name
     *
     * @param name String as param
     * @return     {@link UserRole} entity
     */
    @Override
    public UserRole findByName(String name) {
        UserRole role = entityManager.createQuery("select r from UserRole r where r.name = :name", UserRole.class)
                .setParameter("name", name)
                .getSingleResult();
        return role;
    }
}

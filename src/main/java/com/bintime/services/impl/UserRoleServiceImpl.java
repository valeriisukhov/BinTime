package com.bintime.services.impl;

import com.bintime.dao.UserRoleDao;
import com.bintime.entity.UserRole;
import com.bintime.services.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>User Role Service</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserRoleService
 * @see    UserRoleDao
 * @see    UserRole
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRoleDao userRoleDao;

    public UserRole find(Long id) {
        return userRoleDao.find(id);
    }

    @Transactional
    public void add(UserRole userRole) {
        userRoleDao.add(userRole);
    }

    @Transactional
    public UserRole update(UserRole userRole) {
        return userRoleDao.update(userRole);
    }

    @Transactional
    public void remove(Long id) {
        UserRole userRole = userRoleDao.find(id);
        userRoleDao.remove(userRole);
    }

    public List<UserRole> list() {
        return userRoleDao.list();
    }

    @Override
    public UserRole findByName(String name) {
        UserRole role = userRoleDao.findByName(name.trim().toLowerCase());
        if(role == null)
            throw new UsernameNotFoundException("UserRole "+name+" not found");
        return role;
    }
}

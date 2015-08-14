package com.bintime.dao.impl;

import com.bintime.dao.UserDao;
import com.bintime.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <p>App User db logic implementation</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    User
 * @see    UserDao
 * @see    DaoImpl
 */
@Repository
public class UserDaoImpl extends DaoImpl<User> implements UserDao {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    public UserDaoImpl() {
        super(User.class);
    }

    /**
     * Find user by login
     *
     * @param login login value
     * @return      entity {@link User}
     */
    @Override
    public User findByLogin(String login) {
        User user = entityManager.createQuery("select u from User u where u.login = :login and enabled=true", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }
}

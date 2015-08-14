package com.bintime.dao;


import com.bintime.entity.User;

/**
 * <p>User object db logic</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    User
 * @see    Dao
 */
public interface UserDao extends Dao<User>{
    User findByLogin(String login);
}

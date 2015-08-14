package com.bintime.services.impl;

import com.bintime.dao.UserDao;
import com.bintime.dto.UserDto;
import com.bintime.entity.User;
import com.bintime.services.EntityService;
import com.bintime.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * <p>User Service Implementation</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserService
 * @see    User
 * @see    UserDao
 * @see    EntityService
 */
@Service("userService")
public class UserServiceImpl implements UserService, EntityService<User> {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByLogin(username);
        if(user == null)
            throw new UsernameNotFoundException("User "+username+" not found");
        return UserDto.create(user);
    }

    public User find(Long id) {
        return userDao.find(id);
    }

    @Transactional
    public void add(User user) {
        userDao.update(user);
    }

    @Transactional
    public User update(User user) {
        User result = userDao.update(user);
        return result;
    }

    @Transactional
    public void remove(Long id) {
        User user = userDao.find(id);
        userDao.remove(user);
    }

    public List<User> list() {
        return userDao.list();
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = this.list();
        if (users.isEmpty()){
            return null;
        }
        return UserDto.create(users);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}

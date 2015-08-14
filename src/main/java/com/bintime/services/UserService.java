package com.bintime.services;

import com.bintime.dto.UserDto;
import com.bintime.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>App User Service interface</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    User
 * @see    UserDetailsService
 * @see    EntityService
 */
public interface UserService extends UserDetailsService, EntityService<User>{
    List<UserDto> findAll();
    User findByLogin(String login);
}

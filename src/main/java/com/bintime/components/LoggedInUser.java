package com.bintime.components;

import com.bintime.dto.UserDto;
import com.bintime.entity.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * <p>Logged in user bean for fast access to security context of session.</p>
 * <p>This class describe UserDto model that implements UserDetails bean.</p>
 *
 * @author Valerii Sukhov valerii.sukhov@gmail.com
 * @see    UserDto
 * @see    org.springframework.security.core.userdetails.UserDetails
 */

@Component
public class LoggedInUser {
    private final UserDto user;

    // Suppresses default constructor, ensuring non-instantiability.
    public LoggedInUser() {
        this.user = null;
    }
    // Constructor that innit field user.
    public LoggedInUser(UserDto user) {
        this.user = user;
    }

    /**
     * This method have access to SecurityContextHolder and pick up auth principal.
     * If current session user will not contains any authorities under this level
     * method will return null value.
     *
     * @return this method return UserDto model that describe current session authorities.
     *
     * @see    UserDto
     */
    public UserDto getCurrentUser() {
        if(user != null) {
            return user;
        }

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object principal = auth.getPrincipal();

		    if (principal instanceof UserDto) {
		    	return (UserDto) principal;
		    }
		}
	    return null;
	}

    /**
     * This method have access to SecurityContextHolder and pick up auth principal.
     * If current session user will not contains any authorities under this level
     * method will return null value.
     *
     * @return this method return UserDto model that describe current session authorities.
     *
     * @see    UserDto
     */
    public UserDto getUser(){
        UserDto dto = getCurrentUser();
        return dto;
    }

    /**
     * This method have check SecurityContextHolder and auth session current user.
     *
     * @return if result UserDto model will have auth principals method will return true.
     */
    public boolean isLoggedIn(){
        return getCurrentUser() != null;
    }

    /**
     * This method have check security session on user role.
     *
     * @param role role user as String
     * @return if result UserDto model will have role will return true.
     */
    public boolean hasRole(String role){
        UserDto userDto = getCurrentUser();
        if(userDto ==  null){
            return false;
        }
        int index = userDto.getRoles().indexOf(new UserRole(role));
        return index >= 0;
    }

    /**
     * Is current user have role ROLE_ADMIN will return true.
     *
     * @return if result UserDto model is admin will return true.
     */
    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    /**
     * Is current user have role ROLE_USER will return true.
     *
     * @return if result UserDto model is user will return true.
     */
    public boolean isUser() {
        return hasRole("ROLE_USER");
    }
}

package org.tolking.animeharbor.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.exception.UserAlreadyExists;

import javax.management.relation.RoleNotFoundException;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);

    void saveUser(RegisterDto registerDto, Provider provider) throws RoleNotFoundException, UserAlreadyExists;
    void updateUserPassword(String username, PasswordDto passwordDto);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

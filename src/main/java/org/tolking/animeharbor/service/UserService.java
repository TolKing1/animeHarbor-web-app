package org.tolking.animeharbor.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.dto.user.UserDetailDTO;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;

import javax.management.relation.RoleNotFoundException;
import java.io.FileNotFoundException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetailDTO findByUsername(String username);
    Optional<User> findByEmail(String email);
    User findById(long id);

    Optional<User> saveUser(RegisterDto registerDto, Provider provider) throws RoleNotFoundException, FileNotFoundException;
    void updateUserPassword(String username, PasswordDto passwordDto);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

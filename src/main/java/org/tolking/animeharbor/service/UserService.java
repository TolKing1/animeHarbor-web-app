package org.tolking.animeharbor.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.dto.user.UserDTO;
import org.tolking.animeharbor.dto.user.UserDetailDTO;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;

import javax.management.relation.RoleNotFoundException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<UserDTO> getUsers();
    UserDetailDTO findByUsername(String username);
    Optional<User> findByEmail(String email);
    User findById(long id);

    Optional<User> saveUser(RegisterDto registerDto, Provider provider) throws RoleNotFoundException, FileNotFoundException;
    void updateUserPassword(String username, PasswordDto passwordDto);

    void enableAdminRole(long userId);
    void disableAdminRole(long userId);
    void enableUser(long userId, Authentication authentication);
    void disableUser(long userId, Authentication authentication);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

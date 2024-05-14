package org.tolking.animeharbor.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tolking.animeharbor.entities.User;

import javax.management.relation.RoleNotFoundException;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);
    User save(User user) throws RoleNotFoundException;
}

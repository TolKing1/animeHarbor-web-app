package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.Roles;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.entities.enums.RoleType;
import org.tolking.animeharbor.repositories.RoleRepository;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found by username: " + username)
                );
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found by email: " + email)
                );
    }

    @Override
    public User findById(long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found by id: " + id)
                );
    }

    @Override
    public void save(RegisterDto registerDto) throws RoleNotFoundException {
        Roles roleObg = roleRepository.findByRole(RoleType.USER).orElseThrow(() -> new RoleNotFoundException("Role Not Found:  " + RoleType.USER));
        Optional<User> existUser = userRepository.findByUsername(registerDto.getUserName());

        if (existUser.isEmpty()) {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setUsername(registerDto.getUserName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setProvider(Provider.LOCAL);
            user.setEnabled(true);

            user.addRole(roleObg);

            userRepository.save(user);
        }
    }


    @Override
    public void processOAuthPostLogin(String email, String username) throws RoleNotFoundException {
        Optional<User> existUser = userRepository.findByUsername(username);
        Roles roleObg = roleRepository.findByRole(RoleType.USER).orElseThrow(() -> new RoleNotFoundException("Role Not Found:  " + RoleType.USER));

        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            newUser.addRole(roleObg);

            userRepository.save(newUser);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();
            return new org.springframework.security.core.userdetails.User(
                    userObj.getUsername(),
                    userObj.getPassword(),
                    mapAuthorities(userObj.getRoles())
            );
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private Collection<GrantedAuthority> mapAuthorities(List<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole().toString())).collect(Collectors.toList());
    }

}

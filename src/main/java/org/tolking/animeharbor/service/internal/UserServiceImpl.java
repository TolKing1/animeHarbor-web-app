package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.Roles;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.entities.enums.RoleType;
import org.tolking.animeharbor.repositories.RoleRepository;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.ImageService;
import org.tolking.animeharbor.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.tolking.animeharbor.service.seeder.ImageSeeder.DEFAULT_PROFILE_IMG;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
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
    public void saveUser(RegisterDto registerDto, Provider provider) throws RoleNotFoundException, FileNotFoundException {
        Roles roleObg = roleRepository.findByRole(RoleType.USER).orElseThrow(() -> new RoleNotFoundException("Role Not Found:  " + RoleType.USER));
        Image image = imageService.findImageByName(DEFAULT_PROFILE_IMG).orElseThrow(() -> new FileNotFoundException("IMG Not Found:  " + DEFAULT_PROFILE_IMG));

        Optional<User> existUser = userRepository.findByUsernameOrEmail(registerDto.getUserName(), registerDto.getEmail());

        createIfExists(registerDto, provider, existUser, image, roleObg);
    }

    @Override
    public void updateUserPassword(String username, PasswordDto passwordDto){
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()){
            User existingUser = user.get();
            existingUser.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

            userRepository.save(existingUser);
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

    private void createIfExists(RegisterDto registerDto, Provider provider, Optional<User> existUser, Image image, Roles roleObg) {
        if (existUser.isEmpty()) {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setUsername(registerDto.getUserName());
            user.setProvider(provider);
            user.setEnabled(true);
            user.setImage(image);

            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            user.addRole(roleObg);

            userRepository.save(user);
        }
    }

}

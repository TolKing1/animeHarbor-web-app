package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.dto.user.UserDTO;
import org.tolking.animeharbor.dto.user.UserDetailDTO;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.Roles;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.entities.enums.RoleType;
import org.tolking.animeharbor.exception.AccountIsDisabledException;
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

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_ROLE;
import static org.tolking.animeharbor.constant.ControllerConstant.SUPER_ADMIN_ROLE;
import static org.tolking.animeharbor.service.seeder.ImageSeeder.DEFAULT_PROFILE_IMG;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final DTOConverter<User, UserDetailDTO> userDetailDTOConverter;
    private final DTOConverter<User, UserDTO> userDTOConverter;

    public static Collection<GrantedAuthority> mapAuthorities(List<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsers() {
        return userDTOConverter.convertToDtoList(userRepository.getAllBy());
    }

    @Override
    public UserDetailDTO findByUsername(String username) {
        User user = userRepository.findByUsernameEquals(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found by username: " + username));
        return userDetailDTOConverter.convertToDto(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found by id: " + id));
    }

    @Override
    public Optional<User> saveUser(RegisterDto registerDto, Provider provider) throws RoleNotFoundException, FileNotFoundException {
        Roles role = roleRepository.findByRole(RoleType.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException("Role Not Found: " + RoleType.ROLE_USER));
        Image image = imageService.findImageByName(DEFAULT_PROFILE_IMG)
                .orElseThrow(() -> new FileNotFoundException("Image Not Found: " + DEFAULT_PROFILE_IMG));

        return userRepository.findByUsernameOrEmail(registerDto.getUserName(), registerDto.getEmail()).
                or(() -> createUser(registerDto, provider, image, role));
    }

    @Override
    public void updateUserPassword(String username, PasswordDto passwordDto) {
        userRepository.findByUsernameEquals(username)
                .ifPresent(user -> {
                    user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
                    userRepository.save(user);
                });
    }

    @Override
    public void enableAdminRole(long userId) {
        addOrRemoveRole(userId, true);
    }

    @Override
    public void disableAdminRole(long userId) {
        addOrRemoveRole(userId, false);
    }

    private void addOrRemoveRole(long userId, boolean isAdding) {
        userRepository.findById(userId)
                .ifPresent(user -> roleRepository.findByRole(RoleType.ROLE_ADMIN)
                        .ifPresent(roles -> {
                            addOrRemove(isAdding, user, roles);
                            userRepository.save(user);
                        }));
    }

    private static void addOrRemove(boolean isAdding, User user, Roles roles) {
        if (isAdding){
            user.addRole(roles);
        }else {
            user.removeRole(roles);
        }
    }

    @Override
    public void enableUser(long userId, Authentication auth) {
        enableOrDisableUser(userId, auth,true);
    }

    @Override
    public void disableUser(long userId, Authentication auth) {
        enableOrDisableUser(userId, auth,false);
    }

    private void enableOrDisableUser(long userId, Authentication auth, boolean status) {
        var role = getListAuthorities(auth);
        userRepository.findById(userId)
                .ifPresent(user -> {
                    checkRole(status, user, role);
                    userRepository.save(user);
                });
    }

    private static void checkRole(boolean status, User user, List<String> role) {
        if (!user.hasRole(SUPER_ADMIN_ROLE)){
            if (role.contains(SUPER_ADMIN_ROLE)){

                user.setEnabled(status);
            } else if (role.contains(ADMIN_ROLE)){
                if (!user.hasRole(ADMIN_ROLE)){

                    user.setEnabled(status);
                }
            }
        }
    }

    private static List<String> getListAuthorities(Authentication auth) {
        return auth.getAuthorities().stream().map(GrantedAuthority::toString).toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsernameEquals(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!user.isEnabled()) {
            throw new AccountIsDisabledException("Your account is disabled");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapAuthorities(user.getRoles()))
                .build();
    }

    private Optional<User> createUser(RegisterDto registerDto, Provider provider, Image image, Roles role) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUserName());
        user.setProvider(provider);
        user.setEnabled(true);
        user.setImage(image);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.addRole(role);

        return Optional.of(userRepository.save(user));
    }


}

package org.tolking.animeharbor.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.entities.Roles;
import org.tolking.animeharbor.entities.enums.RoleType;
import org.tolking.animeharbor.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(RoleSeeder.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }
    private void loadRoles() {
        List<RoleType> roleTypes = List.of(
                RoleType.USER,
                RoleType.ADMIN,
                RoleType.SUPER_ADMIN
        );

        roleTypes.forEach((roleName) -> {
            Optional<Roles> rolesOptional = roleRepository.findByRole(roleName);
            if (rolesOptional.isPresent()) {
                logger.info("ROLE EXISTS: {}", roleName);
            }else {
                Roles roleToCreate = new Roles();

                roleToCreate.setRole(roleName);

                roleRepository.save(roleToCreate);
                logger.info("CREATED ROLE : {}", roleName);
            }
        });
    }
}

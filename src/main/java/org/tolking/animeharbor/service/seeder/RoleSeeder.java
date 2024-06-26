package org.tolking.animeharbor.service.seeder;

import jakarta.annotation.Nullable;
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
    public void onApplicationEvent(@Nullable ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        List<RoleType> roleTypes = List.of(
                RoleType.ROLE_USER,
                RoleType.ROLE_ADMIN,
                RoleType.ROLE_SUPER_ADMIN
        );

        roleTypes.forEach((roleName) -> {
            Optional<Roles> rolesOptional = roleRepository.findByRole(roleName);
            if (rolesOptional.isPresent()) {
                logger.info("ROLE EXISTS: {}", roleName);
            } else {
                Roles roleToCreate = new Roles();

                roleToCreate.setRole(roleName);

                roleRepository.save(roleToCreate);
                logger.warn("CREATED ROLE : {}", roleName);
            }
        });
    }
}

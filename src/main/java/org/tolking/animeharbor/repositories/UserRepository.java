package org.tolking.animeharbor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> getAllBy(Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String username);

}
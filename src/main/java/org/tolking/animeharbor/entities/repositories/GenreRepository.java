package org.tolking.animeharbor.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tolking.animeharbor.entities.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllByOrderByTitleAsc();
}

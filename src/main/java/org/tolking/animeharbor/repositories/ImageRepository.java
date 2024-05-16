package org.tolking.animeharbor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.Image;

import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    public Optional<Image> findById(long id);
    public Optional<Image> findByFilename(String name);
}

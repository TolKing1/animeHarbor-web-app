package org.tolking.animeharbor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.Studio;

import java.util.List;

@Repository
public interface StudioRepository extends CrudRepository<Studio, Long> {
    List<Studio> findAllBy();
}

package org.tolking.animeharbor.service;

import org.tolking.animeharbor.entities.Studio;

import java.util.List;
import java.util.Optional;

public interface StudioService {
    Optional<Studio> getStudioById(long id);

    List<Studio> getAllStudios();

    void save(Studio studio);

    void delete(Studio studio);
}

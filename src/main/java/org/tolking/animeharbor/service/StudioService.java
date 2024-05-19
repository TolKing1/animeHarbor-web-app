package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.StudioDTO;

import java.util.List;
import java.util.Optional;

public interface StudioService {
    Optional<StudioDTO> getStudioById(long id);

    List<StudioDTO> getAllStudios();

    void updateOrSave(StudioDTO studio);

    void delete(StudioDTO studio);
}

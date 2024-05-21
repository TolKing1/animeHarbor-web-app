package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.studio.StudioAnimeRegisterDTO;
import org.tolking.animeharbor.dto.studio.StudioDTO;

import java.util.List;
import java.util.Optional;

public interface StudioService {
    Optional<StudioDTO> getStudioById(long id);

    List<StudioDTO> getAllStudios();
    List<StudioAnimeRegisterDTO> getAllStudioName();

    void updateOrSave(StudioDTO studio);

    void delete(StudioDTO studio);
}

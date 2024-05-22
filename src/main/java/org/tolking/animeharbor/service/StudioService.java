package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.studio.StudioAnimeRegisterDTO;
import org.tolking.animeharbor.dto.studio.StudioDTO;
import org.tolking.animeharbor.dto.studio.StudioUpdateDTO;
import org.tolking.animeharbor.entities.Studio;

import java.util.List;
import java.util.Optional;

public interface StudioService {
    Optional<StudioDTO> getStudioById(long id);

    Optional<StudioUpdateDTO> getStudioByIdForUpdate(long id);

    Optional<Studio> findById(long id);

    List<StudioDTO> getAllStudios();

    List<StudioAnimeRegisterDTO> getAllStudioName();

    void save(StudioDTO studio);

    void update(StudioUpdateDTO studio);

    void delete(StudioDTO studio);
}

package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Studio;
import org.tolking.animeharbor.repositories.StudioRepository;
import org.tolking.animeharbor.service.StudioService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudioServiceImpl implements StudioService {
    private final StudioRepository studioRepository;

    @Override
    public Optional<Studio> getStudioById(long id) {
        return studioRepository.findById(id);
    }

    @Override
    public List<Studio> getAllStudios() {
        return studioRepository.findAllBy();
    }

    @Override
    public void save(Studio studio) {
        studioRepository.save(studio);
    }

    @Override
    public void delete(Studio studio) {
        studioRepository.delete(studio);
    }

}

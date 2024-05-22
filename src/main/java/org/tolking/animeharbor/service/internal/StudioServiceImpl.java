package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.studio.StudioAnimeRegisterDTO;
import org.tolking.animeharbor.dto.studio.StudioDTO;
import org.tolking.animeharbor.entities.Studio;
import org.tolking.animeharbor.repositories.StudioRepository;
import org.tolking.animeharbor.service.StudioService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudioServiceImpl implements StudioService {
    private final StudioRepository studioRepository;
    private final DTOConverter<Studio, StudioDTO> dtoConverter;
    private final DTOConverter<Studio, StudioAnimeRegisterDTO> animeRegisterDTOConverter;

    @Override
    public Optional<StudioDTO> getStudioById(long id) {
        Optional<Studio> studioOptional = studioRepository.findById(id);
        return studioOptional.map(dtoConverter::convertToDto);
    }
    @Override
    public Optional<Studio> findById(long id) {
        return studioRepository.findById(id);
    }
    @Override
    public List<StudioDTO> getAllStudios() {
        return dtoConverter.convertToDtoList(studioRepository.getAllBy());
    }

    @Override
    public List<StudioAnimeRegisterDTO> getAllStudioName() {
        return animeRegisterDTOConverter.convertToDtoList(studioRepository.getAllBy());
    }

    @Override
    public void updateOrSave(StudioDTO studioDTO) {
        studioRepository.findById(studioDTO.getId())
                .ifPresentOrElse(studio -> {
                            studio.setName(studioDTO.getName());
                            studio.setDescription(studioDTO.getDescription());

                            studioRepository.save(studio);
                        },
                        () -> studioRepository.save(dtoConverter.convertToEntity(studioDTO))
                );
    }

    @Override
    public void delete(StudioDTO studioDTO) {
        Studio studio = dtoConverter.convertToEntity(studioDTO);
        studioRepository.delete(studio);
    }

}

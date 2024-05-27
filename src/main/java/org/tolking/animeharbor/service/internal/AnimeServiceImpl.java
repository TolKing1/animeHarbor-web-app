package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.anime.AnimeAdminPageDTO;
import org.tolking.animeharbor.dto.anime.AnimeDTO;
import org.tolking.animeharbor.dto.anime.AnimeRegisterDTO;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.dto.studio.StudioAnimeRegisterDTO;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.Studio;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.repositories.GenreRepository;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.ImageService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.tolking.animeharbor.service.seeder.ImageSeeder.DEFAULT_ANIME_IMG;
import static org.tolking.animeharbor.service.seeder.ImageSeeder.DEFAULT_PROFILE_IMG;

@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepository animeRepository;
    private final GenreRepository genreRepository;
    private final ImageService imageService;

    private final DTOConverter<Anime, AnimeDTO> dtoConverter;
    private final DTOConverter<Anime, AnimeAdminPageDTO> adminPageDTOConverter;
    private final DTOConverter<Anime, AnimeRegisterDTO> registerDTOConverter;
    private final DTOConverter<Studio, StudioAnimeRegisterDTO> studioDTOConverter;

    private static Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
    }

    @Override
    public Optional<AnimeDTO> getAnimeById(long id) {
        Optional<Anime> animeOptional = getAnimeOptional(id);

        return animeOptional.map(dtoConverter::convertToDto);
    }

    @Override
    public Optional<AnimeRegisterDTO> getAnimeByIdForAdmin(long id) {
        Optional<Anime> animeOptional = getAnimeOptional(id);
        return animeOptional.map(registerDTOConverter::convertToDto);
    }

    private Optional<Anime> getAnimeOptional(long id) {
        return animeRepository.findById(id);
    }

    @Override
    public void saveAnime(AnimeRegisterDTO animeDTO) {
        animeRepository.findById(animeDTO.getId())
                .ifPresentOrElse(anime -> updateExistingAnime(anime, animeDTO),
                        () -> saveNewAnime(animeDTO));
    }

    private void updateExistingAnime(Anime anime, AnimeRegisterDTO animeDTO) {
        removeGenreAssociation(anime);

        List<Genre> genreList = getGenreList(animeDTO.getGenre());

        anime.setTitle(animeDTO.getTitle());
        anime.setDescription(animeDTO.getDescription());
        anime.setType(AnimeType.valueOf(animeDTO.getType()));
        anime.setDate(animeDTO.getDate());
        anime.setDirector(animeDTO.getDirector());
        anime.setStatus(AnimeStatus.valueOf(animeDTO.getStatus()));
        anime.setGenre(genreList);
        anime.setStudio(studioDTOConverter.convertToEntity(animeDTO.getStudio()));

        animeRepository.save(anime);
    }

    private void removeGenreAssociation(Anime anime) {
        anime.getGenre().forEach(genre -> {
            genre.getAnimeList().remove(anime);
            genreRepository.save(genre);
        });
    }

    private void saveNewAnime(AnimeRegisterDTO animeDTO) {
        try {
            Anime anime = registerDTOConverter.convertToEntity(animeDTO);
            Image image = imageService.findImageByName(DEFAULT_ANIME_IMG)
                    .orElseThrow(() -> new FileNotFoundException("Image Not Found: " + DEFAULT_PROFILE_IMG));
            anime.setStudio(studioDTOConverter.convertToEntity(animeDTO.getStudio()));
            anime.setGenre(getGenreList(animeDTO.getGenre()));
            anime.setImage(image);
            animeRepository.save(anime);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Genre> getGenreList(List<GenreNameDTO> genreDTOSet) {
        List<Genre> genreList = new ArrayList<>();

        for (GenreNameDTO genreDTO : genreDTOSet) {
            Optional<Genre> optionalGenre = genreRepository.findById(genreDTO.getId());

            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                genreList.add(genre);
            }
        }
        return genreList;
    }

    @Override
    public void deleteAnime(AnimeRegisterDTO animeDTO) {
        animeRepository.findById(animeDTO.getId())
                .ifPresent(anime -> {
                    Image image = anime.getImage();
                    String imageName = image.getFilename();

                    animeRepository.delete(anime);

                    if (!imageName.equalsIgnoreCase(DEFAULT_ANIME_IMG)) {
                        imageService.delete(image);
                    }
                });
    }

    @Override
    public List<AnimeAdminPageDTO> getAllForAdminPage() {
        return adminPageDTOConverter.convertToDtoList(animeRepository.getAllBy());
    }

    @Override
    public List<AnimeDTO> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return dtoConverter.convertToDtoList(animeRepository.getAllBy(pageable));
    }

    @Override
    public Page<AnimeDTO> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sortedAnimeDTOPage(query, pageNo, pageSize, sortField, sortDirection, pageable);
    }

    @Override
    public List<AnimeDTO> getAllForRecentlyAddedPage() {
        return getAllAnime(0, 6, "created", "desc");
    }

    @Override
    public List<AnimeDTO> getAllForPopularityPage() {
        Pageable pageable = PageRequest.of(0, 6);
        List<Anime> animeList = animeRepository.getAllByPopularityForLast3Month(pageable);

        return dtoConverter.convertToDtoList(animeList);
    }

    @Override
    public List<AnimeDTO> getAllForHeroPage() {
        Pageable pageable = PageRequest.of(0, 4);
        List<Anime> animeList = animeRepository.getAllByOrderByAverageRating(pageable);
        return dtoConverter.convertToDtoList(animeList);
    }

    @Override
    public List<AnimeDTO> getAllForTopViewPage() {
        List<Anime> animeList = animeRepository.getAllByOrderByViews(PageRequest.of(0, 6));

        return dtoConverter.convertToDtoList(animeList);
    }

    @Override
    public Page<AnimeDTO> getSortedAnimeDTOPageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sortAnimeDTOPageByGenreId(genreId, pageNo, pageSize, sortField, sortDirection, pageable);
    }

    private Page<AnimeDTO> sortedAnimeDTOPage(String query, int pageNo, int pageSize, String sortField, String sortDirection, Pageable pageable) {
        Page<Anime> page;
        if (sortField.equalsIgnoreCase("views")) {
            page = getAnimePageByView(sortDirection, animeRepository.searchFullTextOrderByCountViewsDesc(query, pageable), animeRepository.searchFullTextOrderByCountViewsAsc(query, pageable));
        } else if (sortField.equalsIgnoreCase("rating")) {
            page = getAnimePageByView(sortDirection, animeRepository.searchFullTextOrderByAverageRatingScoreDesc(query, pageable), animeRepository.searchFullTextOrderByAverageRatingScoreAsc(query, pageable));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            page = animeRepository.searchFullText(query, pageable);
        }
        return page.map(dtoConverter::convertToDto);
    }

    private Page<AnimeDTO> sortAnimeDTOPageByGenreId(long genreId, int pageNo, int pageSize, String sortField, String sortDirection, Pageable pageable) {
        Page<Anime> page;
        if (sortField.equalsIgnoreCase("views")) {
            page = getAnimePageByView(sortDirection, animeRepository.findByGenreIdOrderByCountViewsDesc(genreId, pageable), animeRepository.findByGenreIdOrderByCountViewsAsc(genreId, pageable));
        } else if (sortField.equalsIgnoreCase("rating")) {
            page = getAnimePageByView(sortDirection, animeRepository.findByGenreIdOrderByAverageRatingsScoreDesc(genreId, pageable), animeRepository.findByGenreIdOrderByAverageRatingsScoreAsc(genreId, pageable));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            page = animeRepository.findByGenreId(genreId, pageable);
        }
        return page.map(dtoConverter::convertToDto);
    }

    private Page<Anime> getAnimePageByView(String sortDirection, Page<Anime> animeRepository, Page<Anime> animeRepository1) {
        if (sortDirection.equalsIgnoreCase("desc")) {
            return animeRepository;
        } else {
            return animeRepository1;
        }
    }


}

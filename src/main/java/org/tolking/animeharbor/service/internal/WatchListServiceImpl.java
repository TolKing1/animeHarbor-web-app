package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.anime.AnimeDTO;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WatchListServiceImpl implements org.tolking.animeharbor.service.WatchListService {
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;
    private final DTOConverter<Anime, AnimeDTO> dtoConverter;

    @Override
    public List<AnimeDTO> getList(String username) {
        return userRepository.findByUsernameEquals(username)
                .map(user -> dtoConverter.convertToDtoList(user.getWatchList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public void addToList(long animeId, String username) {
        Optional<Anime> animeOptional = animeRepository.findById(animeId);
        Optional<User> userOptional = userRepository.findByUsernameEquals(username);

        animeOptional.ifPresent(anime ->
                userOptional.ifPresent(user -> {
                    user.getWatchList().add(anime);
                    userRepository.save(user);
                })
        );
    }

    @Override
    public void removeFromList(long animeId, String username) {
        Optional<User> userOptional = userRepository.findByUsernameEquals(username);

        userOptional.ifPresent(user -> {
            user.getWatchList()
                    .removeIf(anime -> anime.getId() == animeId);

            userRepository.save(user);
        });
    }

    @Override
    public boolean isAdded(long animeId, Principal principal) {
        if (isNull(principal)) {
            return false;
        }

        return animeRepository.findById(animeId)
                .flatMap(anime -> userRepository.findByUsernameEquals(principal.getName())
                        .map(user -> user.getWatchList().contains(anime)))
                .orElse(false);
    }
}

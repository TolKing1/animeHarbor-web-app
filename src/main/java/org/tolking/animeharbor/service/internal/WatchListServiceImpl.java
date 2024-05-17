package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Override
    public List<Anime> getList(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return user.getWatchList();
        }
        return new ArrayList<>();
    }

    @Override
    public void addToList(long animeId, String username) {
        Optional<Anime> animeOptional = animeRepository.findById(animeId);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (animeOptional.isPresent() && userOptional.isPresent()) {
            User user = userOptional.get();
            Anime anime = animeOptional.get();

            List<Anime> animeList = user.getWatchList();
            animeList.add(anime);

            userRepository.save(user);
        }
    }

    @Override
    public void removeFromList(long animeId, String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Anime> animeList = user.getWatchList();
            animeList.removeIf(anime -> anime.getId() == animeId);

            userRepository.save(user);
        }
    }

    @Override
    public boolean isAdded(long animeId, Principal principal) {
        if (isNull(principal)){
            return false;
        }

        Optional<Anime> animeOptional = animeRepository.findById(animeId);
        Optional<User> userOptional = userRepository.findByUsername(principal.getName());

        if (animeOptional.isPresent() && userOptional.isPresent()) {
            User user = userOptional.get();
            List<Anime> animeList = user.getWatchList();
            return animeList.contains(animeOptional.get());
        }
        else{
            return false;
        }
    }
}

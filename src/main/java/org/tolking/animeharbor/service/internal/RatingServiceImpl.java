package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Rating;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.exception.AnimeNotFoundException;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.repositories.RatingRepository;
import org.tolking.animeharbor.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements org.tolking.animeharbor.service.RatingService {
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;
    private final RatingRepository ratingRepository;

    @Override
    public void save(String username, long animeId, int score) throws AnimeNotFoundException {
        Optional<Rating> ratingOptional = ratingRepository.findRatingByAnimeIdAndUserUsername(animeId, username);

        Rating rating = createOrUpdateRating(username, animeId, score, ratingOptional);

        ratingRepository.save(rating);

    }

    private Rating createOrUpdateRating(String username, long animeId, int score, Optional<Rating> ratingOptional) throws AnimeNotFoundException {
        Rating rating;
        if (ratingOptional.isPresent()) {
            rating = ratingOptional.get();

        } else {
            rating = new Rating();
            User user = userRepository.findByUsernameEquals(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
            Anime anime = animeRepository.findById(animeId)
                    .orElseThrow(() -> new AnimeNotFoundException("Not found"));

            rating.setUser(user);
            rating.setAnime(anime);

        }
        rating.setScore(score);
        return rating;
    }
}

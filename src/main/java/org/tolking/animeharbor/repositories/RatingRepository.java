package org.tolking.animeharbor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.Rating;

import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
    Optional<Rating> findRatingByAnimeIdAndUserUsername(long animeId, String username);
}

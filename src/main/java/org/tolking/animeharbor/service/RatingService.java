package org.tolking.animeharbor.service;

import org.tolking.animeharbor.exception.AnimeNotFoundException;

public interface RatingService {
    void save(String username, long animeId, int score) throws AnimeNotFoundException;
}

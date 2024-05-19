package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.AnimeDTO;

import java.security.Principal;
import java.util.List;

public interface WatchListService {
    List<AnimeDTO> getList(String username);

    void addToList(long animeId, String username);

    void removeFromList(long animeId, String username);

    boolean isAdded(long animeId, Principal principal);
}

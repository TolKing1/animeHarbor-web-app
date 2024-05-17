package org.tolking.animeharbor.service;

import org.tolking.animeharbor.entities.Anime;

import java.security.Principal;
import java.util.List;

public interface WatchListService {
    List<Anime> getList(String username);

    void addToList(long animeId, String username);

    void removeFromList(long animeId, String username);

    boolean isAdded(long animeId, Principal principal);
}

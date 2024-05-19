package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.CommentDTO;
import org.tolking.animeharbor.exception.AnimeNotFoundException;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getLast5Comments(long animeId);

    void save(String userName, long animeId, String commentMsg) throws AnimeNotFoundException;
}

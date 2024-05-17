package org.tolking.animeharbor.service;

import org.tolking.animeharbor.entities.Comment;
import org.tolking.animeharbor.exception.AnimeNotFoundException;

import java.util.List;

public interface CommentService {
    List<Comment> getLast5Comments(long animeId);

    void save(String userName, long animeId, String commentMsg) throws AnimeNotFoundException;
}

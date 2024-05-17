package org.tolking.animeharbor.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByAnimeIdOrderByCreatedAtDesc(long animeId, Pageable pageable);
}

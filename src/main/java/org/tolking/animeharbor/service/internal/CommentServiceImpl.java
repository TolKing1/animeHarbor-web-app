package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Comment;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.exception.AnimeNotFoundException;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.repositories.CommentRepository;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public List<Comment> getLast5Comments(long animeId) {
        Pageable pageable = PageRequest.of(0, 5);
        return commentRepository.findByAnimeIdOrderByCreatedAtDesc(animeId, pageable);
    }

    public void save(String userName, long animeId, String commentMsg) throws AnimeNotFoundException {

        Comment comment = getComment(userName, animeId, commentMsg);

        commentRepository.save(comment);
    }

    private Comment getComment(String username, long animeId, String commentMsg) throws AnimeNotFoundException {
        Comment comment = new Comment();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new AnimeNotFoundException("Not found"));

        comment.setUser(user);
        comment.setAnime(anime);
        comment.setComment(commentMsg);
        return comment;
    }
}

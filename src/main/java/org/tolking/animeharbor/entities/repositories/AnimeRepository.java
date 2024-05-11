package org.tolking.animeharbor.entities.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tolking.animeharbor.entities.Anime;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> getAllBy(Pageable pageable);
    @Query(value =
            "SELECT a.*,COUNT(v.anime_id) as view_count " +
            "FROM anime a " +
            "JOIN views v ON a.id = v.anime_id " +
            "WHERE v.view_date >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '3 month') " +
            "GROUP BY a.id " +
            "ORDER BY view_count DESC;",
            nativeQuery = true)
    List<Anime> getAllByPopularityForLast3Month(Pageable pageable);

    @Query("SELECT a FROM Anime a ORDER BY SIZE(a.views) DESC")
    List<Anime> getAllByOrderByViews(Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN FETCH a.genre g WHERE g.id = ?1 ORDER BY SIZE(a.views) ASC")
    Page<Anime> getAllByGenreIdOrderByViewsAsc(long id, Pageable pageable);
    @Query("SELECT a FROM Anime a JOIN FETCH a.genre g WHERE g.id = ?1 ORDER BY SIZE(a.views) DESC")
    Page<Anime> getAllByGenreIdOrderByViewsDesc(long id, Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN FETCH a.ratings r WHERE r.id = ?1 GROUP BY a.id ORDER BY AVG(r.score) ASC")
    Page<Anime> getAllByGenreIdOrderByRatingAsc(long id, Pageable pageable);
    @Query("SELECT a FROM Anime a JOIN FETCH a.ratings r WHERE r.id = ?1 GROUP BY a.id ORDER BY AVG(r.score) DESC")
    Page<Anime> getAllByGenreIdOrderByRatingDesc(long id, Pageable pageable);

    Page<Anime> findByGenreId(long id, Pageable pageable);
}

package org.tolking.animeharbor.entities.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tolking.animeharbor.entities.Anime;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> getAllBy(Pageable pageable);

    @Query(value =
            "SELECT a.*, COALESCE(COUNT(v.anime_id), 0) AS view_count " +
                    "FROM anime a " +
                    "LEFT JOIN views v ON a.id = v.anime_id AND v.view_date >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '3 month') " +
                    "GROUP BY a.id " +
                    "ORDER BY view_count DESC",
            nativeQuery = true)
    List<Anime> getAllByPopularityForLast3Month(Pageable pageable);

    @Query("SELECT a FROM Anime a ORDER BY SIZE(a.views) DESC")
    List<Anime> getAllByOrderByViews(Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN a.genre g WHERE g.id = :genreId ORDER BY SIZE(a.views) ASC")
    Page<Anime> findByGenreIdOrderByCountViewsAsc(@Param("genreId") long id, Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN a.genre g WHERE g.id = :genreId ORDER BY SIZE(a.views) DESC")
    Page<Anime> findByGenreIdOrderByCountViewsDesc(@Param("genreId") long id, Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN a.genre g LEFT JOIN a.ratings r WHERE g.id = :genreId GROUP BY a.id ORDER BY COALESCE(AVG(r.score),0) ASC")
    Page<Anime> findByGenreIdOrderByAverageRatingsScoreAsc(@Param("genreId") long id, Pageable pageable);

    @Query("SELECT a FROM Anime a JOIN a.genre g LEFT JOIN a.ratings r WHERE g.id = :genreId GROUP BY a.id ORDER BY COALESCE(AVG(r.score),0) DESC")
    Page<Anime> findByGenreIdOrderByAverageRatingsScoreDesc(@Param("genreId") long id, Pageable pageable);

    Page<Anime> findByGenreId(long id, Pageable pageable);
}

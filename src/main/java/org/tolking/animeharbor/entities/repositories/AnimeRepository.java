package org.tolking.animeharbor.entities.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tolking.animeharbor.entities.Anime;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> getAllBy(Pageable pageable);

    List<Anime> getAllBy(Sort sort);

    @Query(value =
            "SELECT a.*,COUNT(v.anime_id) as view_count " +
            "FROM anime a " +
            "JOIN views v ON a.id = v.anime_id " +
            "WHERE v.view_date >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '3 month') " +
            "GROUP BY a.id " +
            "ORDER BY view_count DESC;",
            nativeQuery = true)
    List<Anime> getAllByPopularityForLast3Month(Pageable pageable);

    @Query(value =
            "SELECT a.*, COUNT(v.anime_id) as view_count " +
            "FROM anime a " +
            "JOIN views v ON a.id = v.anime_id " +
            "GROUP BY a.id " +
            "ORDER BY view_count DESC",
            nativeQuery = true)
    List<Anime> getAllByOrderByViewsDesc(Pageable pageable);

    List<Anime> findByGenreId(long id, Pageable pageable);
}

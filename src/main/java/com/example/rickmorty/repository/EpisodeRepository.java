package com.example.rickmorty.repository;

import com.example.rickmorty.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    boolean existsByUrl(String url);

    Episode findByUrl(String url);
}

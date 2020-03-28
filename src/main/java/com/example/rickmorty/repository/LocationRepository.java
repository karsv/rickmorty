package com.example.rickmorty.repository;

import com.example.rickmorty.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByUrl(String url);

    Location findByUrl(String url);
}

package com.example.rickmorty.repository;

import com.example.rickmorty.model.RickMortyCharacter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RickMortyCharacterRepository extends JpaRepository<RickMortyCharacter, Long> {
    @Query(value = "SELECT count(id) FROM characters", nativeQuery = true)
    Integer countAllCharacters();

    List<RickMortyCharacter> findByNameLike(String name);
}

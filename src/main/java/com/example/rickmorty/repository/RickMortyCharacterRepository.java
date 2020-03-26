package com.example.rickmorty.repository;

import com.example.rickmorty.model.RickMortyCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RickMortyCharacterRepository extends JpaRepository<RickMortyCharacter, Long> {

    @Query(value = "SELECT count(id) FROM characters", nativeQuery = true)
    public Integer countAllCharacters();
}

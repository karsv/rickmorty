package com.example.rickmorty.service;

import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.model.RickMortyCharacterNamesResponseDto;
import java.util.List;

public interface RickMortyService {
    Integer countAllCharacters();

    List<RickMortyCharacter> findByNameLike(String name);

    List<RickMortyCharacter> saveAll(List<RickMortyCharacter> characters);

    RickMortyCharacter save(RickMortyCharacter character);

    void deleteAll();

    List<RickMortyCharacter> findAll();

    RickMortyCharacter getRandomCharacter();

    List<RickMortyCharacterNamesResponseDto> getCharactersNamesByRegExp(String name);
}

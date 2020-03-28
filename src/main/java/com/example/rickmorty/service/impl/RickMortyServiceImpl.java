package com.example.rickmorty.service.impl;

import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.model.RickMortyCharacterNamesResponseDto;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.service.RickMortyService;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RickMortyServiceImpl implements RickMortyService {
    @Autowired
    private RickMortyCharacterRepository rickMortyCharacterRepository;

    @Override
    public Integer countAllCharacters() {
        return rickMortyCharacterRepository.countAllCharacters();
    }

    @Override
    public List<RickMortyCharacter> findByNameLike(String name) {
        return rickMortyCharacterRepository.findByNameLike(name);
    }

    @Override
    public List<RickMortyCharacter> saveAll(List<RickMortyCharacter> characters) {
        return rickMortyCharacterRepository.saveAll(characters);
    }

    @Override
    public RickMortyCharacter save(RickMortyCharacter character) {
        return rickMortyCharacterRepository.save(character);
    }

    @Override
    public void deleteAll() {
        rickMortyCharacterRepository.deleteAll();
    }

    @Override
    public List<RickMortyCharacter> findAll() {
        return rickMortyCharacterRepository.findAll();
    }

    @Override
    public RickMortyCharacter getRandomCharacter() {
        Random random = new Random();
        int randomCharacter = random.nextInt(rickMortyCharacterRepository.countAllCharacters());
        return rickMortyCharacterRepository.findById((long) randomCharacter).get();
    }

    @Override
    public List<RickMortyCharacterNamesResponseDto> getCharactersNamesByRegExp(String name) {
        StringBuilder searchPattern = new StringBuilder();
        searchPattern.append("%").append(name).append("%");
        List<RickMortyCharacter> list = rickMortyCharacterRepository
                .findByNameLike(searchPattern.toString());
        return list.stream()
                .map(c -> {
                    RickMortyCharacterNamesResponseDto rm
                            = new RickMortyCharacterNamesResponseDto();
                    rm.setName(c.getName());
                    return rm;
                })
                .collect(Collectors.toList());
    }
}

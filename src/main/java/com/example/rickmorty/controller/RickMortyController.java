package com.example.rickmorty.controller;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.model.RickMortyCharacterResponseDto;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.util.HttpConnection;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rickmorty")
@ApiOperation(value = "rickmorty")
public class RickMortyController {
    private final RickMortyCharacterRepository rickMortyCharacterRepository;
    private final HttpConnection httpConnection;

    @Value(value = "${rickmorty.url}")
    private String url;

    public RickMortyController(RickMortyCharacterRepository rickMortyCharacterRepository,
                               HttpConnection httpConnection) {
        this.rickMortyCharacterRepository = rickMortyCharacterRepository;
        this.httpConnection = httpConnection;
    }

    @GetMapping("/characters")
    public List<RickMortyCharacterResponseDto> getCharactersNamesByRegExp(
            @RequestParam String name) {
        StringBuilder searchPattern = new StringBuilder();
        searchPattern.append("%").append(name).append("%");
        List<RickMortyCharacter> list = rickMortyCharacterRepository
                .findByNameLike(searchPattern.toString());
        return list.stream()
                .map(c -> {
                    RickMortyCharacterResponseDto rm = new RickMortyCharacterResponseDto();
                    rm.setName(c.getName());
                    return rm;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/random")
    public String getRandomCharacter() {
        Random random = new Random();
        int randomCharacter = random.nextInt(rickMortyCharacterRepository.countAllCharacters());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append("/").append(randomCharacter);
        try {
            return httpConnection.sendGet(stringBuilder.toString());
        } catch (IOException e) {
            throw new HttpDataException("Can't get data", e);
        }
    }
}

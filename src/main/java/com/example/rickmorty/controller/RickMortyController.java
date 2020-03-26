package com.example.rickmorty.controller;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.util.HttpConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.swagger.annotations.ApiOperation;
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

    public RickMortyController(RickMortyCharacterRepository rickMortyCharacterRepository,
                               HttpConnection httpConnection) {
        this.rickMortyCharacterRepository = rickMortyCharacterRepository;
        this.httpConnection = httpConnection;
    }

    @Value(value = "${rickmorty.url}")
    private String url;

    @GetMapping("/characters")
    public List<String> getCharactersNamesByRegExp(@RequestParam String name) {
        return new ArrayList<>();
    }

    @GetMapping
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

package com.example.rickmorty.controller;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.util.HttpConnection;
import com.example.rickmorty.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final HttpConnection httpConnection;
    private final JsonParser jsonParser;
    private final RickMortyCharacterRepository rickMortyCharacterRepository;

    @Value("${rickmorty.url}")
    private String url;
    @Value("${rickmorty.page.url}")
    private String pageUrl;

    public InitController(HttpConnection httpConnection,
                          JsonParser jsonParser,
                          RickMortyCharacterRepository rickMortyCharacterRepository) {
        this.httpConnection = httpConnection;
        this.jsonParser = jsonParser;
        this.rickMortyCharacterRepository = rickMortyCharacterRepository;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            int pages = jsonParser.getNumberOfPages(httpConnection.sendGet(url));
            List<RickMortyCharacter> result = new ArrayList<>();
            for (int i = 1; i < pages + 1; i++) {
                result.addAll(jsonParser.getJsonArray(httpConnection.sendGet(pageUrl + i)));
            }
            rickMortyCharacterRepository.saveAll(result);
        } catch (IOException e) {
            throw new HttpDataException("Can't get data from rickmorty!", e);
        }
    }
}

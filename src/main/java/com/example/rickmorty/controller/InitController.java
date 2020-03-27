package com.example.rickmorty.controller;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.model.Episode;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.service.EpisodeService;
import com.example.rickmorty.service.LocationService;
import com.example.rickmorty.service.RickMortyService;
import com.example.rickmorty.util.HttpConnection;
import com.example.rickmorty.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final HttpConnection httpConnection;
    private final JsonParser jsonParser;
    private final RickMortyService rickMortyService;
    private final LocationService locationService;
    private final EpisodeService episodeService;


    @Value("${rickmorty.url}")
    private String url;
    @Value("${rickmorty.page.url}")
    private String pageUrl;

    public InitController(HttpConnection httpConnection,
                          JsonParser jsonParser,
                          RickMortyCharacterRepository rickMortyCharacterRepository,
                          RickMortyService rickMortyService,
                          LocationService locationService,
                          EpisodeService episodeService) {
        this.httpConnection = httpConnection;
        this.jsonParser = jsonParser;
        this.rickMortyService = rickMortyService;
        this.locationService = locationService;
        this.episodeService = episodeService;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            int pages = jsonParser.getNumberOfPages(httpConnection.sendGet(url));
            List<RickMortyCharacter> result = new ArrayList<>();
            for (int i = 1; i <= pages; i++) {
                List<RickMortyCharacter> list = jsonParser.getJsonArray(httpConnection.sendGet(pageUrl + i));
                result.addAll(jsonParser.getJsonArray(httpConnection.sendGet(pageUrl + i)));
            }
            result.stream().forEach(c -> {
                Set<Episode> set = new HashSet<>();
                for (Episode episode : set) {
                    episodeService.save(episode);
                }
            });

//            rickMortyService.saveAll(result);
        } catch (IOException e) {
            throw new HttpDataException("Can't get data from rickmorty!", e);
        }
    }
}

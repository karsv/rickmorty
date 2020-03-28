package com.example.rickmorty.service.impl;

import com.example.rickmorty.model.Episode;
import com.example.rickmorty.model.Location;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.service.EpisodeService;
import com.example.rickmorty.service.LocationService;
import com.example.rickmorty.service.ParseUrlToDbService;
import com.example.rickmorty.service.RickMortyService;
import com.example.rickmorty.util.HttpConnection;
import com.example.rickmorty.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ParseUrlToDbServiceImpl implements ParseUrlToDbService {
    private final HttpConnection httpConnection;
    private final JsonParser jsonParser;
    private final RickMortyService rickMortyService;
    private final LocationService locationService;
    private final EpisodeService episodeService;

    public ParseUrlToDbServiceImpl(HttpConnection httpConnection,
                                   JsonParser jsonParser,
                                   RickMortyService rickMortyService,
                                   LocationService locationService,
                                   EpisodeService episodeService) {
        this.httpConnection = httpConnection;
        this.jsonParser = jsonParser;
        this.rickMortyService = rickMortyService;
        this.locationService = locationService;
        this.episodeService = episodeService;
    }

    @Override
    public List<RickMortyCharacter> parseUrlToDbService(String url) throws IOException {
        List<RickMortyCharacter> result = new ArrayList<>();

        int pages = jsonParser.getNumberOfPages(httpConnection.sendGet(url));
        for (int i = 1; i <= pages; i++) {
            List<RickMortyCharacter> list
                    = jsonParser.getJsonArray(httpConnection.sendGet(url + i));
            for (RickMortyCharacter c : list) {
                RickMortyCharacter characterTemp = c;
                Set<Episode> episodesTemp = new HashSet<>();
                episodeService
                        .saveAll(c.getEpisode())
                        .forEach(episode -> episodesTemp.add(episode));
                characterTemp.setEpisode(episodesTemp);

                Location locationTemp = locationService.save(c.getLocation());
                characterTemp.setLocation(locationTemp);

                Location originTemp = locationService.save(c.getOrigin());
                characterTemp.setOrigin(originTemp);

                result.add(characterTemp);
                rickMortyService.save(characterTemp);
            }
        }
        return result;
    }
}

package com.example.rickmorty.configuration;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.service.ParseUrlToDbService;
import com.example.rickmorty.service.RickMortyService;
import com.example.rickmorty.util.HttpConnection;
import com.example.rickmorty.util.JsonParser;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    private final RickMortyService rickMortyService;
    private final ParseUrlToDbService parseUrlToDbService;
    private final JsonParser jsonParser;
    private final HttpConnection httpConnection;

    @Value("${rickmorty.page.url}")
    private String pageUrl;

    public ScheduleConfig(RickMortyService rickMortyService,
                          ParseUrlToDbService parseUrlToDbService,
                          JsonParser jsonParser,
                          HttpConnection httpConnection) {
        this.rickMortyService = rickMortyService;
        this.parseUrlToDbService = parseUrlToDbService;
        this.jsonParser = jsonParser;
        this.httpConnection = httpConnection;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    private void scheduled() {
        List<RickMortyCharacter> list = rickMortyService.findAll();
        try {
            Integer charactersInCurrentDb = rickMortyService.countAllCharacters();
            Integer charactersInApi = jsonParser.getQuantityOfCharacters(
                    httpConnection.sendGet(pageUrl));
            if (!charactersInApi.equals(charactersInCurrentDb)) {
                rickMortyService.deleteAll();
                parseUrlToDbService.parseUrlToDbService(pageUrl);
            }
        } catch (IOException e) {
            rickMortyService.deleteAll();
            rickMortyService.saveAll(list);
            throw new HttpDataException("Can't get data from rickmorty!", e);
        }
    }
}

package com.example.rickmorty.configuration;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.repository.RickMortyCharacterRepository;
import com.example.rickmorty.util.HttpConnection;
import com.example.rickmorty.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    private final HttpConnection httpConnection;
    private final JsonParser jsonParser;
    private final RickMortyCharacterRepository rickMortyCharacterRepository;

    @Value("${rickmorty.url}")
    private String url;
    @Value("${rickmorty.page.url}")
    private String pageUrl;

    public ScheduleConfig(HttpConnection httpConnection, JsonParser jsonParser,
                          RickMortyCharacterRepository rickMortyCharacterRepository) {
        this.httpConnection = httpConnection;
        this.jsonParser = jsonParser;
        this.rickMortyCharacterRepository = rickMortyCharacterRepository;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    private void scheduled() {
        List<RickMortyCharacter> list = rickMortyCharacterRepository.findAll();
        try {
            Integer charactersInCurrentDb = rickMortyCharacterRepository.countAllCharacters();
            Integer charactersInApi = jsonParser.getQuantityOfCharacters(httpConnection.sendGet(url));
            if (!charactersInApi.equals(charactersInCurrentDb)) {
                int pages = jsonParser.getNumberOfPages(httpConnection.sendGet(url));
                List<RickMortyCharacter> result = new ArrayList<>();
                for (int i = 1; i < pages + 1; i++) {
                    result.addAll(jsonParser.getJsonArray(httpConnection.sendGet(pageUrl + i)));
                }
                rickMortyCharacterRepository.deleteAll();
                rickMortyCharacterRepository.saveAll(result);
            }
        } catch (IOException e) {
            rickMortyCharacterRepository.deleteAll();
            rickMortyCharacterRepository.saveAll(list);
            throw new HttpDataException("Can't get data from rickmorty!", e);
        }
    }
}

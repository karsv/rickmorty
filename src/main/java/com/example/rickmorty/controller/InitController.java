package com.example.rickmorty.controller;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.service.ParseUrlToDbService;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final ParseUrlToDbService parseUrlToDbService;

    @Value("${rickmorty.url}")
    private String url;
    @Value("${rickmorty.page.url}")
    private String pageUrl;

    public InitController(ParseUrlToDbService parseUrlToDbService) {
        this.parseUrlToDbService = parseUrlToDbService;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            parseUrlToDbService.parseUrlToDbService(pageUrl);
        } catch (IOException e) {
            throw new HttpDataException("Can't get data from rickmorty!", e);
        }
    }
}

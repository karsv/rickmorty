package com.example.rickmorty.service;

import com.example.rickmorty.model.RickMortyCharacter;
import java.io.IOException;
import java.util.List;

public interface ParseUrlToDbService {
    List<RickMortyCharacter> parseUrlToDbService(String url) throws IOException;
}

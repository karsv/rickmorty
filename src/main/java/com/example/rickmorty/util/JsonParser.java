package com.example.rickmorty.util;

import com.example.rickmorty.model.RickMortyCharacter;
import java.util.List;

public interface JsonParser {
    Integer getQuantityOfCharacters(String json);

    Integer getNumberOfPages(String json);

    List<RickMortyCharacter> getJsonArray(String json);
}

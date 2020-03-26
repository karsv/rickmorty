package com.example.rickmorty.util.impl;

import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.util.JsonParser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonParserImpl implements JsonParser {
    @Override
    public Integer getQuantityOfCharacters(String json) {
        JSONObject obj = new JSONObject(json);
        return obj.getJSONObject("info").getInt("count");
    }

    @Override
    public Integer getNumberOfPages(String json) {
        JSONObject obj = new JSONObject(json);
        return obj.getJSONObject("info").getInt("pages");
    }

    @Override
    public List<RickMortyCharacter> getJsonArray(String json) {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("results");
        List<RickMortyCharacter> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            RickMortyCharacter rickMortyCharacter = new RickMortyCharacter();
            rickMortyCharacter.setId(Long.valueOf(arr.getJSONObject(i).getLong("id")));
            rickMortyCharacter.setName(arr.getJSONObject(i).getString("name"));
            list.add(rickMortyCharacter);
        }
        return list;
    }
}

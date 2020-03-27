package com.example.rickmorty.util.impl;

import com.example.rickmorty.model.Episode;
import com.example.rickmorty.model.Location;
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
            rickMortyCharacter.setStatus(arr.getJSONObject(i).getString("status"));
            rickMortyCharacter.setSpecies(arr.getJSONObject(i).getString("species"));
            rickMortyCharacter.setType(arr.getJSONObject(i).getString("type"));
            rickMortyCharacter.setGender(arr.getJSONObject(i).getString("gender"));

            JSONObject originObj = arr.getJSONObject(i).getJSONObject("origin");
            Location origin = new Location();
            origin.setName(originObj.getString("name"));
            origin.setUrl(originObj.getString("url"));
            rickMortyCharacter.setOrigin(origin);

            JSONObject locationObj = arr.getJSONObject(i).getJSONObject("location");
            Location location = new Location();
            location.setName(locationObj.getString("name"));
            location.setUrl(locationObj.getString("url"));
            rickMortyCharacter.setLocation(location);

            rickMortyCharacter.setImage(arr.getJSONObject(i).getString("image"));

            JSONArray episodeArr = arr.getJSONObject(i).getJSONArray("episode");
            for (int j = 0; j < episodeArr.length(); j++) {
                Episode episode = new Episode();
                episode.setUrl(episodeArr.getString(j));
                rickMortyCharacter.addEpisode(episode);
            }

            rickMortyCharacter.setUrl(arr.getJSONObject(i).getString("url"));
            rickMortyCharacter.setCreated(arr.getJSONObject(i).getString("created"));

            list.add(rickMortyCharacter);
        }
        return list;
    }
}

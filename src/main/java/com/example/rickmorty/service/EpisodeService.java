package com.example.rickmorty.service;

import com.example.rickmorty.model.Episode;

public interface EpisodeService {
    Episode save(Episode episode);

    Iterable<Episode> saveAll(Iterable<Episode> episodes);
}

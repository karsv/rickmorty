package com.example.rickmorty.service.impl;

import com.example.rickmorty.model.Episode;
import com.example.rickmorty.repository.EpisodeRepository;
import com.example.rickmorty.service.EpisodeService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Override
    public Episode save(Episode episode) {
        if (!episodeRepository.existsByUrl(episode.getUrl())) {
            return episodeRepository.save(episode);
        }
        return episodeRepository.findByUrl(episode.getUrl());
    }

    @Override
    public Iterable<Episode> saveAll(Iterable<Episode> episodes) {
        Set<Episode> set = new HashSet<>();
        for (Episode episode : episodes) {
            set.add(save(episode));
        }
        return set;
    }
}

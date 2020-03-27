package com.example.rickmorty.service.impl;

import com.example.rickmorty.model.Episode;
import com.example.rickmorty.repository.EpisodeRepository;
import com.example.rickmorty.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Override
    public void save(Episode episode) {
        episodeRepository.save(episode);
    }
}

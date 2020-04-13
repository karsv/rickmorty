package com.example.rickmorty.service.impl;

import com.example.rickmorty.model.Location;
import com.example.rickmorty.repository.LocationRepository;
import com.example.rickmorty.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        if (!locationRepository.existsByUrl(location.getUrl())) {
            return locationRepository.save(location);
        }
        return locationRepository.findByUrl(location.getUrl());
    }
}

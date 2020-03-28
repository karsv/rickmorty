package com.example.rickmorty.controller;

import com.example.rickmorty.model.RickMortyCharacter;
import com.example.rickmorty.model.RickMortyCharacterNamesResponseDto;
import com.example.rickmorty.service.RickMortyService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rickmorty")
@ApiOperation(value = "rickmorty")
public class RickMortyController {
    private final RickMortyService rickMortyService;

    public RickMortyController(RickMortyService rickMortyService) {
        this.rickMortyService = rickMortyService;
    }

    @GetMapping("/characters")
    public List<RickMortyCharacterNamesResponseDto> getCharactersNamesByRegExp(
            @RequestParam String name) {
        return rickMortyService.getCharactersNamesByRegExp(name);
    }

    @GetMapping("/random")
    public RickMortyCharacter getRandomCharacter() {
        return rickMortyService.getRandomCharacter();
    }
}

package com.example.rickmorty.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "characters")
@Data
public class RickMortyCharacter {
    @Id
    private Long id;
    private String name;
}

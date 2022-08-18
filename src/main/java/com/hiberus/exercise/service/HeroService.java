package com.hiberus.exercise.service;

import java.util.List;
import java.util.Optional;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;

public interface HeroService {
    List<HeroDto> getAllHeroes();
    Optional<HeroDto> getHeroById(Long id);
    Optional<List<HeroDto>> getHeroesByName(String name);
    HeroDto saveOrUpdateHero(HeroDto hero);
    void deleteHero(Long id);
}

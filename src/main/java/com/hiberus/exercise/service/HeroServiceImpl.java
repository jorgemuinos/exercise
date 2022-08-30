package com.hiberus.exercise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.mappers.HeroMapper;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.repository.HeroRepository;

@Service
public class HeroServiceImpl implements HeroService{

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;

    public HeroServiceImpl(HeroRepository heroRepository, HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.heroMapper = heroMapper;
    }

    @Override
    public List<HeroDto> getAllHeroes() {
        List<Hero> heroes = heroRepository.findAll();
        return heroes.stream().map(heroMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "heroes")
    public Optional<HeroDto> getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);
        return hero.map(heroMapper::toDto);
    }

    @Override
    public Optional<List<HeroDto>> getHeroesByName(String name) {
        List<Hero> heroes = heroRepository.findAllByNameContainingIgnoreCase(name);
        return Optional.of(heroes.stream().map(heroMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    @CacheEvict(value = "heroes", allEntries = true)
    public HeroDto saveHero(HeroDto heroDto) {
        Hero hero = heroRepository.save(heroMapper.toModel(heroDto));
        return heroMapper.toDto(hero);
    }

    @Override
    @CacheEvict(value = "heroes", key = "#heroDto.id")
    public Optional<HeroDto> updateHero(HeroDto heroDto) {
        Optional<Hero> heroDb = heroRepository.findById(heroDto.getId());
        return heroDb
            .map(h -> {
                Hero updatedHero = heroRepository.save(heroMapper.toModel(heroDto));
                return Optional.of(heroMapper.toDto(updatedHero));
            }
        ).orElseGet(Optional::empty);
    }

    @Override
    @CacheEvict(value = "heroes")
    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }
}

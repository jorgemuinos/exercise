package com.hiberus.exercise.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.repository.HeroRepository;
import org.modelmapper.ModelMapper;

@Service
public class HeroServiceImpl implements HeroService{

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HeroDto> getAllHeroes() {
        List<Hero> heroes = (List<Hero>) heroRepository.findAll();
        return heroes.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "heroes")
    public Optional<HeroDto> getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);
        return hero.map(this::toDto);
    }

    @Override
    public Optional<List<HeroDto>> getHeroesByName(String name) {
        List<Hero> heroes = heroRepository.findAllByNameContainingIgnoreCase(name);
        return Optional.of(heroes.stream().map(this::toDto).collect(Collectors.toList()));
    }

    @Override
    @CacheEvict(value = "heroes", key = "#heroDto.id")
    public HeroDto saveOrUpdateHero(HeroDto heroDto) {
        Hero hero = heroRepository.save(toModel(heroDto));
        return toDto(hero);
    }

    @Override
    @CacheEvict(value = "heroes")
    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }

    private HeroDto toDto(Hero hero){
        return modelMapper.map(hero,HeroDto.class);
    }

    private Hero toModel(HeroDto hero){
        return modelMapper.map(hero,Hero.class);
    }
}

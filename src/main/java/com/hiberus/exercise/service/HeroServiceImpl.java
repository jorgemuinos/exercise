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
    @Cacheable("heroes")
    public List<HeroDto> getAllHeroes() {
        List<Hero> heroes = (List<Hero>) heroRepository.findAll();
        return heroes.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable("heroes")
    public Optional<HeroDto> getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);
        return hero.map(this::toDto);
    }

    @Override
    @Cacheable("heroes")
    public Optional<List<HeroDto>> getHeroesByName(String name) {
        List<Hero> heroes = heroRepository.findByNameContainingIgnoreCase(name);
        return Optional.of(heroes.stream().map(this::toDto).collect(Collectors.toList()));
    }

    @Override
    @CachePut("heroes")
    public HeroDto saveOrUpdateHero(HeroDto hero) {
        Hero heroModel = heroRepository.save(toModel(hero));
        return toDto(heroModel);
    }

    @Override
    @CacheEvict("heroes")
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

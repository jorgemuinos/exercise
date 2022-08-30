package com.hiberus.exercise.mappers;

import org.springframework.stereotype.Component;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;
import org.modelmapper.ModelMapper;

@Component
public class HeroMapper {

    private final ModelMapper modelMapper;

    public HeroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HeroDto toDto(Hero hero){
        return modelMapper.map(hero,HeroDto.class);
    }

    public Hero toModel(HeroDto hero){
        return modelMapper.map(hero,Hero.class);
    }
}

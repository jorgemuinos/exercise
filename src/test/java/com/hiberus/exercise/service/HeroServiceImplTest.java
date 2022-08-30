package com.hiberus.exercise.service;

import java.util.List;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.mappers.HeroMapper;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HeroServiceImplTest {

    @InjectMocks
    private HeroServiceImpl heroService;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private HeroMapper heroMapper;

    @Test
    void getHeroes(){

        //given
        List<Hero> heroesList = List.of(newHero(1), newHero(2));

        when(heroRepository.findAll()).thenReturn(heroesList);
        when(heroMapper.toDto(heroesList.get(0))).thenReturn(newHeroDto(1));

        //when
        List<HeroDto> heroesResponse = heroService.getAllHeroes();

        //then
        assertFalse(heroesResponse.isEmpty());
        assertThat(heroesResponse.size()).isSameAs(2);
        verify(heroRepository, times(1)).findAll();

    }

    private Hero newHero(long id){
        return new Hero(id, "", 0);
    }
    private HeroDto newHeroDto(long id){
        return new HeroDto(id, "", 0);
    }

}

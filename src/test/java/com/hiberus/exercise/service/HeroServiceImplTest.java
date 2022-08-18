package com.hiberus.exercise.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.hiberus.exercise.controller.HeroController;
import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.repository.HeroRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HeroService.class)
@ContextConfiguration
public class HeroServiceImplTest {

    @Autowired
    private HeroService heroService;

    @MockBean
    private HeroRepository heroRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void getHeroes(){
        //given
        List<Hero> heroesList = Arrays.asList(newHero(1), newHero(2));
        given(heroRepository.findAll()).willReturn(((List<Hero>)heroesList));

        //when
        List<HeroDto> heroesResponse = heroService.getAllHeroes();

        //then
        assertThat(heroesResponse.isEmpty()).isFalse();
        assertThat(heroesResponse.size()).isSameAs(2);

    }

    private Hero newHero(long id){
        return new Hero(id, "", 0);
    }

}

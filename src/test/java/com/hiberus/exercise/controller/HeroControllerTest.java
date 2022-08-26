package com.hiberus.exercise.controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.service.HeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HeroController.class)
@AutoConfigureMockMvc(addFilters = false)
class HeroControllerTest {

    @MockBean
    HeroService heroService;

    @Autowired
    MockMvc mvc;

    @Test
    void testGetAllHeroes() throws Exception{
        //given
        List<HeroDto> heroesList = List.of(newHeroDto(1), newHeroDto(2));
        Mockito.when(heroService.getAllHeroes()).thenReturn(heroesList);

        //when
        ResultActions resultActions = mvc.perform(get("/heroes"));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.length()").value(heroesList.size()));

    }

    private HeroDto newHeroDto(long id){
        return new HeroDto(id, "", 0);
    }
}

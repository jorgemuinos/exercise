package com.hiberus.exercise.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.service.HeroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HeroController.class)
@ContextConfiguration
public class HeroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService heroService;

    private JacksonTester<List<HeroDto>> jsonResult;

    final private HttpHeaders httpHeaders = new HttpHeaders();

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

    }

    @Test
    public void getHeroes() throws Exception{
        //given
        List<HeroDto> heroesList = Arrays.asList(newHero(1), newHero(2));
        given(heroService.getAllHeroes()).willReturn(heroesList);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/heroes").headers(httpHeaders)).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResult.write(heroesList).getJson());
    }

    private HeroDto newHero(long id){
        return new HeroDto(id, "", 0);
    }
}

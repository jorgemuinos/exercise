package com.hiberus.exercise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.model.Hero;
import com.hiberus.exercise.service.HeroService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/heroes")
public class HeroController {
    @Autowired
    private HeroService heroService;

    Logger logger = LogManager.getLogger(HeroController.class);

    @GetMapping
    public ResponseEntity<List<HeroDto>> getHeroes(final @RequestParam(value = "name",required = false) String name){
        logger.info("Get heroes");
        if(name != null){
            Optional<List<HeroDto>> heroes = heroService.getHeroesByName(name);
            return heroes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }else{
            return ResponseEntity.ok(heroService.getAllHeroes());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroDto> getHero(final @PathVariable("id") Long id){
        Optional<HeroDto> hero = heroService.getHeroById(id);
        return hero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<HeroDto> updateHero(@RequestBody HeroDto hero){
        return ResponseEntity.ok(heroService.saveOrUpdateHero(hero));
    }

    @PostMapping
    public ResponseEntity<HeroDto> saveHero(@RequestBody HeroDto hero){
        return ResponseEntity.ok(heroService.saveOrUpdateHero(hero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HeroDto> deleteHero(final @PathVariable("id") Long id){
        heroService.deleteHero(id);
        return ResponseEntity.noContent().build();
    }
}

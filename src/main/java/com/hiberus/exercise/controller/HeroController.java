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
import com.hiberus.exercise.anotations.CustomTimed;
import com.hiberus.exercise.service.HeroService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    @Autowired
    private HeroService heroService;

    Logger logger = LogManager.getLogger(HeroController.class);

    @CustomTimed
    @GetMapping
    public ResponseEntity<List<HeroDto>> getHeroes(final @RequestParam(value = "name", required = false) String name) {
        logger.info("Get heroes");
        return Optional.ofNullable(name)
            .map(nameOne -> {
                    Optional<List<HeroDto>> heroes = heroService.getHeroesByName(nameOne);
                    return heroes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
                }
            )
            .orElseGet(() -> ResponseEntity.ok(heroService.getAllHeroes()));
    }

    @CustomTimed
    @GetMapping("/{id}")
    public ResponseEntity<HeroDto> getHero(final @PathVariable("id") Long id) {
        Optional<HeroDto> hero = heroService.getHeroById(id);
        return hero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CustomTimed
    @PutMapping
    public ResponseEntity<HeroDto> updateHero(@RequestBody HeroDto hero) {
        return ResponseEntity.ok(heroService.saveOrUpdateHero(hero));
    }

    @CustomTimed
    @PostMapping
    public ResponseEntity<HeroDto> saveHero(@RequestBody HeroDto hero) {
        return ResponseEntity.ok(heroService.saveOrUpdateHero(hero));
    }

    @CustomTimed
    @DeleteMapping("/{id}")
    public ResponseEntity<HeroDto> deleteHero(final @PathVariable("id") Long id) {
        heroService.deleteHero(id);
        return ResponseEntity.noContent().build();
    }
}

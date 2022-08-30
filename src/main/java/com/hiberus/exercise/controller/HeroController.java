package com.hiberus.exercise.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.groups.Default;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.hiberus.exercise.controller.validators.SaveHeroValidator;
import com.hiberus.exercise.controller.validators.UpdateHeroValidator;
import com.hiberus.exercise.dto.HeroDto;
import com.hiberus.exercise.anotations.CustomTimed;
import com.hiberus.exercise.service.HeroService;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @CustomTimed
    @GetMapping
    public ResponseEntity<List<HeroDto>> getHeroes(final @RequestParam(value = "name", required = false) String name) {
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
    public ResponseEntity<HeroDto> updateHero(@Validated({UpdateHeroValidator.class, Default.class}) @RequestBody HeroDto hero) {
        Optional<HeroDto> heroDto = heroService.updateHero(hero);
        return heroDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CustomTimed
    @PostMapping
    public ResponseEntity<HeroDto> saveHero(@Validated({SaveHeroValidator.class, Default.class}) @RequestBody HeroDto hero) {
        hero = heroService.saveHero(hero);
        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
            .path("/{id}").buildAndExpand(hero.getId()).toUri();
        return ResponseEntity.created(uri).body(hero);
    }

    @CustomTimed
    @DeleteMapping("/{id}")
    public ResponseEntity<HeroDto> deleteHero(final @PathVariable("id") Long id) {
        heroService.deleteHero(id);
        return ResponseEntity.noContent().build();
    }
}

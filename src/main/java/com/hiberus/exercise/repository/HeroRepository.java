package com.hiberus.exercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hiberus.exercise.model.Hero;

public interface HeroRepository extends CrudRepository<Hero, Long> {
    Optional<Hero> findById(Long id);
    List<Hero> findByNameContainingIgnoreCase(String name);
}

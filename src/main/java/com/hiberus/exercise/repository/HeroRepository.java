package com.hiberus.exercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiberus.exercise.model.Hero;

public interface HeroRepository extends JpaRepository<Hero, Long> {
    Optional<Hero> findById(Long id);
    List<Hero> findAllByNameContainingIgnoreCase(String name);
}

package com.hiberus.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import org.modelmapper.ModelMapper;

@SpringBootApplication
@EnableCaching
public class ExerciseApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}
}

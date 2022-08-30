package com.hiberus.exercise.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.hiberus.exercise.controller.validators.SaveHeroValidator;
import com.hiberus.exercise.controller.validators.UpdateHeroValidator;

public class HeroDto implements Serializable {

    @Null(groups = SaveHeroValidator.class)
    @NotNull(groups = UpdateHeroValidator.class)
    private Long id;

    @NotBlank
    private String name;

    private int age;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HeroDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public HeroDto() {
    }

}

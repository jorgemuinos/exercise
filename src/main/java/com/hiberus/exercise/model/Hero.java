package com.hiberus.exercise.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hero {

    public Hero() {
    }

    public Hero(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Id
    private long id;
    private String name;
    private int age;

}

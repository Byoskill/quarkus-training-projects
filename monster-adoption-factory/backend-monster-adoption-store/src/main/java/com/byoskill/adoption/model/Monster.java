package com.byoskill.adoption.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Monster {
    private Integer id;
    private String monsterUUID;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 3, max = 20)
    private String description;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer price;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer age;

    @NotNull
    @Size(min = 3, max = 20)
    private String location;

    public Monster() {
    }

    public String getMonsterUUID() {
        return monsterUUID;
    }

    public void setMonsterUUID(String monsterUUID) {
        this.monsterUUID = monsterUUID;
    }

    private Integer monsterId;

    public Integer getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Integer monsterId) {
        this.monsterId = monsterId;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    

}
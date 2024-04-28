package com.byoskill.domain.adoption.model;

import com.byoskill.domain.common.model.HasName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Monster implements HasName {
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
    private Integer monsterId;

    public Monster() {
    }

    public String getMonsterUUID() {
        return monsterUUID;
    }

    public void setMonsterUUID(final String monsterUUID) {
        this.monsterUUID = monsterUUID;
    }

    public Integer getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(final Integer monsterId) {
        this.monsterId = monsterId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }


}
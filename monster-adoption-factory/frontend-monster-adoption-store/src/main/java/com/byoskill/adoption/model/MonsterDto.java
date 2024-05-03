package com.byoskill.adoption.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MonsterDto {
    private Long id;
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
    private String image_url;

    public MonsterDto() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(final String image_url) {
        this.image_url = image_url;
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    @Override
    public String toString() {
        return "MonsterDto{" +
                "id=" + id +
                ", monsterUUID='" + monsterUUID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", monsterId=" + monsterId +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
package com.byoskill.adoption.model;

public class MonsterForm {
    private String name;
    private String description;
    private Integer price;
    private String location;
    private Integer age;
    private String monsterUuid;
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(final String image_url) {
        this.image_url = image_url;
    }

    public String getMonsterUuid() {
        return monsterUuid;
    }

    public void setMonsterUuid(final String monsterUuid) {
        this.monsterUuid = monsterUuid;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

}

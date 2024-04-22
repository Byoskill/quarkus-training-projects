package com.byoskill.adoption.model;

public class Monster {
    private Integer id;
    private String monsterUUID;
    private String name;
    private String description;
    private Integer price;
    private Integer age;
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
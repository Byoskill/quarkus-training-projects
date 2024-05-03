package com.byoskill.adapters.adoption.h2;

import com.byoskill.domain.adoption.model.Monster;
import jakarta.persistence.*;


@Entity
@Table(name = "monsters")
public class MonsterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String monsterUUID;

    @Column(name = "price")
    private Integer price;

    @Column(name = "age")
    private Integer age;

    @Column(name = "location")
    private String location;

    @Column(name = "monsterId")
    private Integer monsterId;

    // Getters and Setters
    public MonsterEntity() {
    }

    public static MonsterEntity fromModel(final Monster monster) {
        final MonsterEntity monsterEntity = new MonsterEntity();
        monsterEntity.id = monster.getId();
        monsterEntity.name = monster.getName();
        monsterEntity.description = monster.getDescription();
        monsterEntity.monsterUUID = monster.getMonsterUUID();
        monsterEntity.price = monster.getPrice();
        monsterEntity.age = monster.getAge();
        monsterEntity.location = monster.getLocation();
        monsterEntity.monsterId = monster.getMonsterId();
        return monsterEntity;
    }

    public Monster toModel() {
        final Monster monster = new Monster();
        monster.setId(id);
        monster.setName(name);
        monster.setDescription(description);

        monster.setMonsterUUID(monsterUUID);
        monster.setPrice(price);
        monster.setAge(age);
        monster.setLocation(location);
        monster.setMonsterId(monsterId);
        return monster;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMonsterUUID() {
        return monsterUUID;
    }

    public void setMonsterUUID(final String monsterUUID) {
        this.monsterUUID = monsterUUID;
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

    public Integer getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(final Integer monsterId) {
        this.monsterId = monsterId;
    }
}

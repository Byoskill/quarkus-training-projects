package adoption.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

// Coté backend
public class Monster {
    private Integer id;

    @UUIDValid
    private String monsterUUID;

    @NotBlank
    @Size(min = 4, max = 10)
    private String name;

    @Size(min = 4, max = 100)
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer age;
    @NotBlank
    private String location;
    @NotBlank
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonsterUUID() {
        return monsterUUID;
    }

    public void setMonsterUUID(String monsterUUID) {
        this.monsterUUID = monsterUUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}

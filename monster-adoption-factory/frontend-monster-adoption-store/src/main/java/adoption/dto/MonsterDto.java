package adoption.dto;

// Coté frontend
public record MonsterDto(
        String id,
        String name,
        String description,
        String image_url,
        Integer price) {

    public String getUrl() {
        return this.image_url;
    }
}

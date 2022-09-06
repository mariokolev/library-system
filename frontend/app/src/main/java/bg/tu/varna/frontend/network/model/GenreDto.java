package bg.tu.varna.frontend.network.model;

public class GenreDto {
    private Long id;
    private String name;

    public GenreDto(Long id, String genre) {
        this.id = id;
        this.name = genre;
    }

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

    @Override
    public String toString() {
        return this.getName();
    }
}

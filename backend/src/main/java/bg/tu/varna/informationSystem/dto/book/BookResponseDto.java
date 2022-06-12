package bg.tu.varna.informationSystem.dto.book;

import bg.tu.varna.informationSystem.dto.genre.GenreDto;

public class BookResponseDto {
    private Long id;
    private String title;
    private GenreDto genre;
    private String author;
    private Long readingRoomId;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public void setGenre(GenreDto genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getReadingRoomId() {
        return readingRoomId;
    }

    public void setReadingRoomId(Long readingRoomId) {
        this.readingRoomId = readingRoomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

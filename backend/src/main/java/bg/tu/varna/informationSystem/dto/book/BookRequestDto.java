package bg.tu.varna.informationSystem.dto.book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookRequestDto {

    @NotEmpty
    private String title;

    @NotNull
    private String author;

    @NotNull
    private Long genreId;

    @NotNull
    private Long readingRoomId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getReadingRoomId() {
        return readingRoomId;
    }

    public void setReadingRoomId(Long readingRoomId) {
        this.readingRoomId = readingRoomId;
    }
}

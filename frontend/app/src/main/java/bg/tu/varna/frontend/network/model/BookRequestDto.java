package bg.tu.varna.frontend.network.model;

public class BookRequestDto {
    private String title;
    private String author;
    private Long genreId;
    private Long readingRoomId;

    public BookRequestDto(String title, String author, Long genreId, Long readingRoomId) {
        this.title = title;
        this.author = author;
        this.genreId = genreId;
        this.readingRoomId = readingRoomId;
    }

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

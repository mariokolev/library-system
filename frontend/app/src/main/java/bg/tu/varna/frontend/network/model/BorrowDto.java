package bg.tu.varna.frontend.network.model;

public class BorrowDto {
    private Long id;
    private BookDto book;
    private UserDto reader;
    private UserDto operator;
    private String dateAdded;
    private String dateReturn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public UserDto getReader() {
        return reader;
    }

    public void setReader(UserDto reader) {
        this.reader = reader;
    }

    public UserDto getOperator() {
        return operator;
    }

    public void setOperator(UserDto operator) {
        this.operator = operator;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }
}

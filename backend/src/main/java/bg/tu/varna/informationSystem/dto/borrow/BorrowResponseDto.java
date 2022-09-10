package bg.tu.varna.informationSystem.dto.borrow;


import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;

public class BorrowResponseDto {
    private Long id;
    private BookResponseDto book;
    private UserResponseDto reader;
    private UserResponseDto operator;
    private String dateAdded;
    private String dateDueReturn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateDueReturn() {
        return dateDueReturn;
    }

    public void setDateDueReturn(String dateDueReturn) {
        this.dateDueReturn = dateDueReturn;
    }

    public BookResponseDto getBook() {
        return book;
    }

    public void setBook(BookResponseDto book) {
        this.book = book;
    }

    public UserResponseDto getReader() {
        return reader;
    }

    public void setReader(UserResponseDto reader) {
        this.reader = reader;
    }

    public UserResponseDto getOperator() {
        return operator;
    }

    public void setOperator(UserResponseDto operator) {
        this.operator = operator;
    }
}

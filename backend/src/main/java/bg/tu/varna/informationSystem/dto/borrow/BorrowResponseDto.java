package bg.tu.varna.informationSystem.dto.borrow;


import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;

public class BorrowResponseDto {
    private Long id;
//    private Long bookId;
    private BookResponseDto book;
//    private Long readerId;
    private UserResponseDto reader;
//    private Long operatorId;
    private UserResponseDto operator;
    private String dateAdded;
    private String dateDueReturn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Long bookId) {
//        this.bookId = bookId;
//    }

//    public Long getReaderId() {
//        return readerId;
//    }
//
//    public void setReaderId(Long readerId) {
//        this.readerId = readerId;
//    }
//
//    public Long getOperatorId() {
//        return operatorId;
//    }
//
//    public void setOperatorId(Long operatorId) {
//        this.operatorId = operatorId;
//    }

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

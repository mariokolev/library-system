package bg.tu.varna.frontend.network.model;

public class BorrowRequestDto {
    private Long bookId;
    private Long readerId;
    private String dateDueReturn;

    public BorrowRequestDto() {
    }

    public BorrowRequestDto(Long bookId, Long readerId, String dateDueReturn) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.dateDueReturn = dateDueReturn;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public String getDateDueReturn() {
        return dateDueReturn;
    }

    public void setDateDueReturn(String dateDueReturn) {
        this.dateDueReturn = dateDueReturn;
    }
}

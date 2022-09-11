package bg.tu.varna.frontend.network.model;

public class BorrowReturnDto {
    private Long bookId;
    private String dateReturned;
    private String condition;

    public BorrowReturnDto() {
    }

    public BorrowReturnDto(Long bookId, String dateReturned, String condition) {
        this.bookId = bookId;
        this.dateReturned = dateReturned;
        this.condition = condition;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

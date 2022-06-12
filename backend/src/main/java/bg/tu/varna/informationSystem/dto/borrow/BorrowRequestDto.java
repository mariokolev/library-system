package bg.tu.varna.informationSystem.dto.borrow;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BorrowRequestDto {
    @NotNull
    private Long bookId;

    @NotNull
    private Long readerId;

    @NotEmpty
    private String dateDueReturn;

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

package bg.tu.varna.informationSystem.dto.borrow;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BorrowReturnRequestDto {

    @NotNull
    private Long bookId;

    @NotEmpty
    private String dateReturned;

    private String condition;

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

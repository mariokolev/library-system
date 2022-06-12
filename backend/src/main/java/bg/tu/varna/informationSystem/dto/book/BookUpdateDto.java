package bg.tu.varna.informationSystem.dto.book;

import bg.tu.varna.informationSystem.annotations.ValidEnum;
import bg.tu.varna.informationSystem.common.BookStatuses;
import bg.tu.varna.informationSystem.common.Messages;

import javax.validation.constraints.NotEmpty;

public class BookUpdateDto {
    @NotEmpty
    @ValidEnum(enumClass = BookStatuses.class, ignoreCase = true, message = Messages.INVALID_BOOK_STATUS)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

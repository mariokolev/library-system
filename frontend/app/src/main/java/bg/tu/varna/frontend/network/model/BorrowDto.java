package bg.tu.varna.frontend.network.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BorrowDto implements Parcelable {
    private Long id;
    private BookDto book;
    private UserDto reader;
    private UserDto operator;
    private String dateAdded;
    private String dateReturn;

    protected BorrowDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        book = in.readParcelable(BookDto.class.getClassLoader());
        reader = in.readParcelable(UserDto.class.getClassLoader());
        operator = in.readParcelable(UserDto.class.getClassLoader());
        dateAdded = in.readString();
        dateReturn = in.readString();
    }

    public static final Creator<BorrowDto> CREATOR = new Creator<BorrowDto>() {
        @Override
        public BorrowDto createFromParcel(Parcel in) {
            return new BorrowDto(in);
        }

        @Override
        public BorrowDto[] newArray(int size) {
            return new BorrowDto[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeParcelable(book, i);
        parcel.writeParcelable(reader, i);
        parcel.writeParcelable(operator, i);
        parcel.writeString(dateAdded);
        parcel.writeString(dateReturn);
    }
}

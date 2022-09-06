package bg.tu.varna.frontend.network.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BookDto implements Parcelable {
    private Long id;
    private String title;
    private GenreDto genre;
    private String author;
    private String status;
    private Long readingRoomId;
    private String dateAdded;

    public BookDto(Long id, String title, GenreDto genre, String author, String status, Long readingRoomId, String dateAdded) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.status = status;
        this.readingRoomId = readingRoomId;
        this.dateAdded = dateAdded;
    }

    protected BookDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        author = in.readString();
        status = in.readString();
        if (in.readByte() == 0) {
            readingRoomId = null;
        } else {
            readingRoomId = in.readLong();
        }
        dateAdded = in.readString();
    }

    public static final Creator<BookDto> CREATOR = new Creator<BookDto>() {
        @Override
        public BookDto createFromParcel(Parcel in) {
            return new BookDto(in);
        }

        @Override
        public BookDto[] newArray(int size) {
            return new BookDto[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public void setGenre(GenreDto genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getReadingRoomId() {
        return readingRoomId;
    }

    public void setReadingRoomId(Long readingRoomId) {
        this.readingRoomId = readingRoomId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(status);
        if (readingRoomId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(readingRoomId);
        }
        parcel.writeString(dateAdded);
    }
}

package bg.tu.varna.frontend.network.model;

import androidx.annotation.NonNull;

public class ReadingRoomDto {
    private Long id;
    private String name;
    private Long libraryId;

    public ReadingRoomDto(Long id, String name, Long libraryId) {
        this.id = id;
        this.name = name;
        this.libraryId = libraryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}

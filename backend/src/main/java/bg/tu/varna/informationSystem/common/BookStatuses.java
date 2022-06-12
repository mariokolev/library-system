package bg.tu.varna.informationSystem.common;

public enum BookStatuses {
    AVAILABLE,
    BORROWED,
    ARCHIVED,
    WRITE_OFF;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

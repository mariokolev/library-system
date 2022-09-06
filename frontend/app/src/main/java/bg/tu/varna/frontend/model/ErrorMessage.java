package bg.tu.varna.frontend.model;

public class ErrorMessage {

    private String message;
    private String timestamp;
    private String uri;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}

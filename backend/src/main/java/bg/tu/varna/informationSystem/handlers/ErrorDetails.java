package bg.tu.varna.informationSystem.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorDetails {

    @JsonProperty
    private final String message;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private final LocalDateTime timestamp;

    @JsonProperty("uri")
    private final String uriRequested;

    public ErrorDetails(Exception exception, String uriRequested) {
        this.message = exception.getMessage();
        this.uriRequested = uriRequested;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUriRequested() {
        return uriRequested;
    }
}


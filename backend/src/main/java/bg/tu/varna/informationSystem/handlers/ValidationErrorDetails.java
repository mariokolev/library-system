package bg.tu.varna.informationSystem.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorDetails {

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private final LocalDateTime timestamp;

    @JsonProperty("uri")
    private final String uriRequested;

    @JsonProperty
    private Map<String, String> messages;

    public ValidationErrorDetails(String uriRequested, Map<String, String> messages) {
        this.uriRequested = uriRequested;
        this.timestamp = LocalDateTime.now();
        this.messages = messages;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUriRequested() {
        return uriRequested;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}

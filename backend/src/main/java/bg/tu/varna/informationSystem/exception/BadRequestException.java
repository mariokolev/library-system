package bg.tu.varna.informationSystem.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String... args) {
        super(String.format(message,args));
    }
}

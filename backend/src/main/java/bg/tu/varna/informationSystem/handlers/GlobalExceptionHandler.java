package bg.tu.varna.informationSystem.handlers;

import bg.tu.varna.informationSystem.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> getError(final Exception exception, final HttpStatus httpStatus,
                                                  HttpServletRequest request) {

        return new ResponseEntity<>(new ErrorDetails(exception, request.getRequestURI()), httpStatus);
    }

    private ResponseEntity<ValidationErrorDetails> getValidationErrors(final HttpStatus httpStatus,
                                                                       Map<String, String> messages, HttpServletRequest request) {

        return new ResponseEntity<>(new ValidationErrorDetails(request.getRequestURI(), messages), httpStatus);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(HttpServletRequest request, BadRequestException e) {
        return getError(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDetails> handleValidationExceptions(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return getValidationErrors(HttpStatus.BAD_REQUEST, errors, request);
    }
}

package by.bntu.constructor.web.util.exception;

import by.bntu.constructor.web.util.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class Handler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> notFound(NotFoundException e) {
        return from(e, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> internalServerError(Exception e) {
        return from(e, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({FormatException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseError> badRequest(Exception e) {
        return from(e, BAD_REQUEST);
    }

    private ResponseEntity<ResponseError> from(Exception e, HttpStatus status) {
        ResponseError responseError = new ResponseError(status.value(), e.getMessage());
        return new ResponseEntity<>(responseError, status);
    }

}

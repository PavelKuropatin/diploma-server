package by.bntu.diploma.diagram.web.handler;

import by.bntu.diploma.diagram.service.exception.NotFoundException;
import by.bntu.diploma.diagram.web.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class Handler {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Handler.class);

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ResponseError> handleRestException(RestException e) {
        ResponseEntity<ResponseError> responseEntity = this.from(e);
        LOGGER.error("{} caught.", e.getClass().getSimpleName(), e);
        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> catchException(Exception e) {
        HttpStatus httpStatus = e instanceof NotFoundException ? NOT_FOUND : INTERNAL_SERVER_ERROR;
        RestException restException = new RestException(httpStatus, e);
        return handleRestException(restException);
    }

    private ResponseEntity<ResponseError> from(RestException e) {
        ResponseError responseError = new ResponseError(e.getHttpStatus().value(), e.getMessage());
        return new ResponseEntity<>(responseError, e.getHttpStatus());
    }

}

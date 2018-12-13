package by.bntu.diploma.diagram.web.handler;

import by.bntu.diploma.diagram.service.exception.DiagramNotFoundException;
import by.bntu.diploma.diagram.service.exception.StateNotFoundException;
import by.bntu.diploma.diagram.web.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class Handler {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Handler.class);

    private static final String MESSAGE_SUFFIX = "{} catch in " + Handler.class;

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ResponseError> handleRestException(RestException e) {
        ResponseEntity<ResponseError> responseEntity = this.from(e);
        LOGGER.error(MESSAGE_SUFFIX, e.getClass().getSimpleName(), e);
        return responseEntity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> internalServerException(IllegalArgumentException e) {
        RestException restException = new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        return handleRestException(restException);
    }

    @ExceptionHandler({DiagramNotFoundException.class, StateNotFoundException.class})
    public ResponseEntity<ResponseError> internalServerException(Exception e) {
        RestException restException = new RestException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        return handleRestException(restException);
    }

    private ResponseEntity<ResponseError> from(RestException e) {
        ResponseError responseError = new ResponseError(e.getHttpStatus().toString());
        return new ResponseEntity<>(responseError, e.getHttpStatus());
    }

}

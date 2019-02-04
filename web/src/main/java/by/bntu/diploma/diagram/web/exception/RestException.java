package by.bntu.diploma.diagram.web.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    private HttpStatus httpStatus;

    public RestException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public RestException(HttpStatus httpStatus, Throwable e) {
        this(httpStatus, null, e);
    }

    public RestException(HttpStatus httpStatus, String message, Throwable e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}


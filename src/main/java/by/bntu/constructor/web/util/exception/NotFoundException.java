package by.bntu.constructor.web.util.exception;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(Throwable e) {
        this(e.getMessage(), e);
    }

    public NotFoundException(String message, Throwable e) {
        super(message, e);
    }

}


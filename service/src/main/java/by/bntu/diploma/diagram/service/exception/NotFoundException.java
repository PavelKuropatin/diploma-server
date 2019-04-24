package by.bntu.diploma.diagram.service.exception;

import javax.validation.constraints.NotNull;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(@NotNull Class objClass, Object objectUuid) {
        super(objClass.getSimpleName() + "[" + objectUuid + "] not found.");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}

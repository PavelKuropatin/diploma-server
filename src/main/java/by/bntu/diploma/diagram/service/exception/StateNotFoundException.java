package by.bntu.diploma.diagram.service.exception;

public class StateNotFoundException extends RuntimeException {

    public StateNotFoundException() {
        super();
    }

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateNotFoundException(Throwable cause) {
        super(cause);
    }
}

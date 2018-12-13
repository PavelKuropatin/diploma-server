package by.bntu.diploma.diagram.service.exception;

public class DiagramNotFoundException extends RuntimeException {

    public DiagramNotFoundException() {
        super();
    }

    public DiagramNotFoundException(String message) {
        super(message);
    }

    public DiagramNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiagramNotFoundException(Throwable cause) {
        super(cause);
    }
}

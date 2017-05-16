package by.training.epam.service.util.exception;

public class UtilException extends Exception {
    public UtilException() {
        super();
    }

    public UtilException(String msg) {
        super(msg);
    }

    public UtilException(String msg, Exception e) {
        super(msg, e);
    }
    public UtilException(Exception e) {
        super(e);
    }
}

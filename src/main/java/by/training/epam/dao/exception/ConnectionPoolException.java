package by.training.epam.dao.exception;

public class ConnectionPoolException extends DAOException {
    public static final long serialVersionUID = 12L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String msg) {
        super(msg);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }

    public ConnectionPoolException(String msg, Exception e) {
        super(msg, e);
    }
}

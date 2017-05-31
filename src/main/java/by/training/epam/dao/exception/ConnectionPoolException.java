package by.training.epam.dao.exception;

import by.training.epam.dao.pool.impl.ConnectionPool;

/**
 * The class {@code ConnectionPoolException} indicates conditions that a reasonable
 * application might want to catch in {@link  ConnectionPool}.
 * It extends {@link DAOException} class.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
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

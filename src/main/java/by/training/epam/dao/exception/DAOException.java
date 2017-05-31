package by.training.epam.dao.exception;


/**
 * The class {@code DAOException} and its subclasses are a form of
 * {@code Exception} that indicates conditions that a reasonable
 * application might want to catch in dao layer. It extends {@link Exception} class.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param exception cause (which is saved for later retrieval by the
     *                  {@link #getCause()} method).  (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or
     *                  unknown.)
     * @since 1.4
     */
    public DAOException(Exception exception) {
        super(exception);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message   the detail message (which is saved for later retrieval
     *                  by the {@link #getMessage()} method).
     * @param exception the cause (which is saved for later retrieval by the
     *                  {@link #getCause()} method).  (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or
     *                  unknown.)
     * @since 1.4
     */
    public DAOException(String message, Exception exception) {
        super(message, exception);
    }
}

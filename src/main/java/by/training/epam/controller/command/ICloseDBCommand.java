package by.training.epam.controller.command;

/**
 * Close database operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICloseDBCommand {
    /**
     * close database connections
     */
    void closeDB();
}

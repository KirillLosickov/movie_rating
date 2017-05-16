package by.training.epam.service;

import by.training.epam.service.exception.ServiceException;

/**
 * Simple database operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICloseDB {
    /**
     * Get all connections of database.
     * @throws by.training.epam.service.exception.ServiceException
     */
    void closeConnections() throws ServiceException;
}

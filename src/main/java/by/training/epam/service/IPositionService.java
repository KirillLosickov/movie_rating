package by.training.epam.service;

import by.training.epam.bean.Position;
import by.training.epam.service.exception.ServiceException;

import java.util.List;

/**
 * Simple position operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IPositionService {
    /**
     * Get all positions.
     * @return the list of positions.
     * @throws ServiceException
     */
    List<Position> getAll() throws ServiceException;
}

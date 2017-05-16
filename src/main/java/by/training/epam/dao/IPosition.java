package by.training.epam.dao;

import by.training.epam.bean.Position;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple position operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IPosition {
    /**
     * Get all countries.
     * @return the list of positions.
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Position> getAll() throws DAOException;

}

package by.training.epam.dao;

import by.training.epam.bean.Genre;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple genre operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IGenreDAO {
    /**
     * Get all genres.
     * @return the list of genres.
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Genre> getAll() throws DAOException;
}

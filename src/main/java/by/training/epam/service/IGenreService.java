package by.training.epam.service;

import by.training.epam.bean.Genre;
import by.training.epam.service.exception.ServiceException;

import java.util.List;

/**
 * Simple genre operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IGenreService {

    /**
     * Get all counties.
     * @return the list of countries.
     * @throws by.training.epam.service.exception.ServiceException
     */
    List<Genre> getAll() throws ServiceException;
}

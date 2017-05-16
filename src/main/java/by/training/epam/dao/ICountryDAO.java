package by.training.epam.dao;

import by.training.epam.bean.Country;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple country operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICountryDAO {
    /**
     * Get all countries.
     * @return the list of countries.
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Country> getAll() throws DAOException;

}

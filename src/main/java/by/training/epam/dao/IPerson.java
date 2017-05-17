package by.training.epam.dao;

import by.training.epam.bean.Person;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple person operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IPerson {
    /**
     * Get all countries.
     * @return the list of persons.
     * @throws DAOException
     */
    List<Person> getAll() throws DAOException;
}

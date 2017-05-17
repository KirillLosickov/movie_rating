package by.training.epam.service;

import by.training.epam.bean.Person;
import by.training.epam.service.exception.ServiceException;

import java.util.List;

/**
 * Simple person operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IPersonService {
    /**
     * Get all persons.
     * @return the list of persons.
     * @throws ServiceException
     */
    List<Person> getAll() throws ServiceException;
}

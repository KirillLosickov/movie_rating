package by.training.epam.service;

import by.training.epam.bean.Country;
import by.training.epam.service.exception.ServiceException;

import java.util.List;

/**
 * Simple country operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICountryService {

    /**
     * Get all counties.
     * @return the list of countries.
     * @throws by.training.epam.service.exception.ServiceException
     */
    List<Country> getAll() throws ServiceException;

}

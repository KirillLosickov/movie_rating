package by.training.epam.service.impl;

import by.training.epam.bean.Country;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.service.ICountryService;
import by.training.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;


public class CountryServiceImpl implements ICountryService {
    private static Logger logger = Logger.getLogger(CountryServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public List<Country> getAll() throws ServiceException {
        logger.debug("CountryServiceImpl.getAll()");
        List<Country> countries = null;
        try {
            countries = daoFactory.getICountryDAO().getAll();

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("CountryServiceImpl.getAll() - success");
        return countries;
    }
}

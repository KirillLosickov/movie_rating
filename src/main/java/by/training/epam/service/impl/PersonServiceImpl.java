package by.training.epam.service.impl;

import by.training.epam.bean.Person;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.service.IPersonService;
import by.training.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class PersonServiceImpl implements IPersonService {
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private static Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Override
    public List<Person> getAll() throws ServiceException {
        logger.debug("PersonServiceImpl.getAll()");
        List<Person> persons = null;
        try {
            persons = daoFactory.getIPerson().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("PersonServiceImpl.getAll() - success");
        return persons;
    }
}

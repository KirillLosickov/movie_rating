package by.training.epam.service;

import by.training.epam.bean.Genre;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class GenreServiceImpl implements IGenreService {
    private static Logger logger = Logger.getLogger(CountryServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public List<Genre> getAll() throws ServiceException {
        logger.debug("GenreServiceImpl.getAll()");
        List<Genre> genreList = null;
        try {
            genreList = daoFactory.getIGenreDAO().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("CountryServiceImpl.getAll() - success");
        return genreList;
    }
}

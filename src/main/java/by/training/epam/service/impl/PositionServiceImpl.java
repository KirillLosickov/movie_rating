package by.training.epam.service.impl;

import by.training.epam.bean.Position;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.service.IPositionService;
import by.training.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class PositionServiceImpl implements IPositionService {
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private static Logger logger = Logger.getLogger(PositionServiceImpl.class);
    @Override
    public List<Position> getAll() throws ServiceException {
        logger.debug("PositionServiceImpl.getAll()");
        List<Position> positions = null;
        try {
            positions = daoFactory.getIPosition().getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("PositionServiceImpl.getAll()");
        return positions;
    }
}

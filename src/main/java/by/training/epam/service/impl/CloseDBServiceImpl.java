package by.training.epam.service.impl;

import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.pool.ConnectionPool;
import by.training.epam.dao.pool.ICloseConnectionPool;
import by.training.epam.service.ICloseDB;
import by.training.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

public class CloseDBServiceImpl implements ICloseDB {
    private static Logger logger = Logger.getLogger(CloseDBServiceImpl.class);
    public void closeConnections()throws ServiceException{
        logger.debug("Service.closeConnection()");
        try {
            ICloseConnectionPool pool = ConnectionPool.getInstance();
            pool.releasePool();
        } catch (ConnectionPoolException e) {
            throw new ServiceException(e);
        }
        logger.debug("Service.closeConnection() - success");
    }
}

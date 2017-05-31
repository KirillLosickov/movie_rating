package by.training.epam.controller.command.impl;


import by.training.epam.controller.command.ICloseDBCommand;
import by.training.epam.service.ICloseDB;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.CloseDBServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code CloseDBCommand} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class CloseDBCommand implements ICloseDBCommand {
    private static Logger logger = Logger.getLogger(CloseDBCommand.class);
    /**
     * Override method {@link ICloseDBCommand#closeDB()}
     * Call method {@link ICloseDB#closeConnections()} for closing database's connections.
     * @return path of jsp page
     */
    @Override
    public void closeDB()  {
        try {
            ICloseDB closeDB = new CloseDBServiceImpl();
            closeDB.closeConnections();
        } catch (ServiceException e) {
            logger.error(e);
        }
    }
}

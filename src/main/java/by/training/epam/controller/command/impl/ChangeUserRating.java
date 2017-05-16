package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IUserService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code ChangeUserRating} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ChangeUserRating implements ICommand {
    private static Logger logger = Logger.getLogger(ChangeUserRating.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IUserService#changeUserRating(String, String)} )}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to change user rating");
        IUserService iUserService = serviceFactory.getUserServiceImpl();
        try {
            String idUser = request.getParameter(RequestParameter.ID_USER.getValue());
            String rating = request.getParameter(RequestParameter.RATING.getValue());
            iUserService.changeUserRating(idUser, rating);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "рейтинг изменён");
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" changed user rating");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        } catch (ServiceLogicException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
        }
        return JspPageName.INDEX_PAGE.getPath();
    }
}

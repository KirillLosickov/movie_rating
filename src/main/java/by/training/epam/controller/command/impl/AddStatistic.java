package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IMovieService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code AddMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class AddStatistic implements ICommand {
    private static Logger logger = Logger.getLogger(AddStatistic.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#addStatistic(String, String, String, String)}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to add statistic");
        IMovieService IMovieService = serviceFactory.getMovieServiceImpl();
        try {
            String comment = request.getParameter(RequestParameter.COMMENT.getValue());
            String mark = request.getParameter(RequestParameter.MARK.getValue());
            String idMovie = request.getParameter(RequestParameter.ID_MOVIE.getValue());
            String loginUser = request.getParameter(RequestParameter.LOGIN.getValue());
            IMovieService.addStatistic(loginUser, idMovie, comment, mark);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "Data is added");
            logger.debug(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) +  " added statistic");
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
        } catch (ServiceLogicException e) {
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getMessage());
        }
        return JspPageName.INDEX_PAGE.getPath();
    }
}

package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IMovieService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code SetRemoveMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class SetRemoveMovie implements ICommand {
    private static Logger logger = Logger.getLogger(SetRemoveMovie.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#setRemovedMovie(String)}
     *
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String idMovie = request.getParameter(RequestParameter.ID_MOVIE.getValue());
        IMovieService iMovieService = serviceFactory.getMovieServiceImpl();
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to remove movie");
        try {
            iMovieService.setRemovedMovie(idMovie);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "Film is deleted");
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" removed movie");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.ADMIN_USER_PAGE.getPath();
    }
}

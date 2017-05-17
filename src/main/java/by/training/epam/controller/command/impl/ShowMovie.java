package by.training.epam.controller.command.impl;

import by.training.epam.bean.Comment;
import by.training.epam.bean.Movie;
import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IMovieService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class {@code ShowMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowMovie implements ICommand {
    private static Logger logger = Logger.getLogger(ShowMovie.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#getMovie(String)}
     *
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        JspPageName jspPageName = JspPageName.ERROR_PAGE;
        Movie movie = null;
        List<Comment> comments = null;
        IMovieService IMovieService = serviceFactory.getMovieServiceImpl();
        String idMovie = request.getParameter(RequestParameter.ID_MOVIE.getValue());
        try {
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to reestablish movie");
            comments = IMovieService.getCommentsByMovie(idMovie);
            movie = IMovieService.getMovie(idMovie);
            request.setAttribute(RequestParameter.MOVIE.getValue(), movie);
            request.setAttribute(RequestParameter.COMMENT_LIST.getValue(), comments);
            jspPageName = JspPageName.MOVIE_PAGE;
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" reestablish movie");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return jspPageName.getPath();
    }
}


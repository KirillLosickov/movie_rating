package by.training.epam.controller.command.impl;

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
 * Class {@code ShowMovies} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowMovies implements ICommand {

    private static Logger logger = Logger.getLogger(ShowMovies.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#getPage(String)}
     * Redirect parameters to {@link IMovieService#getNoOfRecords(String)}
     * Redirect parameters to {@link IMovieService#getMovies(int, String)}
     * Redirect parameters to {@link IMovieService#getNoOfPages(int)}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IMovieService iMovieService = serviceFactory.getMovieServiceImpl();
        String pageString = request.getParameter(RequestParameter.PAGINATION.getValue());
        List<Movie> movieList = null;
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to show movies");
        try {
            String position = (String) request.getSession().getAttribute(RequestParameter.POSITION.getValue());
            int page = iMovieService.getPage(pageString);
            movieList = iMovieService.getMovies(page, position);
            int noOfRecords = iMovieService.getNoOfRecords(position);
            int noOfPages = iMovieService.getNoOfPages(noOfRecords);
            request.setAttribute(RequestParameter.MOVIE_LIST.getValue(), movieList);
            request.setAttribute(RequestParameter.NO_OF_PAGES.getValue(), noOfPages);
            request.setAttribute(RequestParameter.CURRENT_PAGE.getValue(), page);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestParameter.SHOW_MOVIES.getValue());
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" showed movies");
        } catch (ClassCastException e) {
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "ошибка приведения типов");
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.ADMIN_USER_PAGE.getPath();
    }
}

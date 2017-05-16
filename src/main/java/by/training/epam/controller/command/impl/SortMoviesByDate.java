package by.training.epam.controller.command.impl;

import by.training.epam.bean.Movie;
import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.*;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class {@code SortMoviesByDate} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class SortMoviesByDate implements ICommand {
    private static Logger logger = Logger.getLogger(SortMoviesByDate.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#getPage(String)},
     * {@link IMovieService#getNoOfPages(int)}, {@link IMovieService#getNoOfRecords(String)},
     * {@link IMovieService#sortByDate(int, String),
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to sort movies by date");
        IMovieService iMovieService = serviceFactory.getMovieServiceImpl();
        String pageString = request.getParameter(RequestParameter.PAGINATION.getValue());
        List<Movie> movieList = null;
        try {
            String position = (String) request.getSession().getAttribute(RequestParameter.POSITION.getValue());
            int page = iMovieService.getPage(pageString);
            movieList = iMovieService.sortByDate(page, position);
            int noOfRecords = iMovieService.getNoOfRecords(position);
            int noOfPages = iMovieService.getNoOfPages(noOfRecords);
            request.setAttribute(RequestParameter.MOVIE_LIST.getValue(), movieList);
            request.setAttribute(RequestParameter.NO_OF_PAGES.getValue(), noOfPages);
            request.setAttribute(RequestParameter.CURRENT_PAGE.getValue(), page);
            request.setAttribute(RequestParameter.COMMAND.getValue(), RequestParameter.SORT_FILMS_BY_DATE.getValue());
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" sorted movies by date");
        } catch (ClassCastException e) {
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "ошибка приведения типов");
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
        } catch (ServiceException e) {
            logger.error(((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())), e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.ADMIN_USER_PAGE.getPath();
    }
}

package by.training.epam.controller.command.impl;

import by.training.epam.bean.*;
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
 * Class {@code ShowPageForAddMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowPageForAddMovie implements ICommand {

    private static Logger logger = Logger.getLogger(ShowPageForAddMovie.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Call methods {@link IMovieService#getAllMoviesTypes()}, {@link ICountryService#getAll()},
     * {@link IGenreService#getAll()}, {@link IPositionService#getAll()},
     * {@link IPersonService#getAll()}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Genre> genres = null;
        List<Country> countries = null;
        List<Position> positions = null;
        List<Person> persons = null;
        List<Movie.TypeMovie> movieTypes = null;
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to get page for add movie");
        try {
            IMovieService movieService = serviceFactory.getMovieServiceImpl();
            ICountryService countryService = serviceFactory.getCountryServiceImpl();
            IGenreService genreService = serviceFactory.getGenreServiceImpl();
            IPositionService positionService = serviceFactory.getPositionService();
            IPersonService personService = serviceFactory.getPersonServiceImpl();
            positions = positionService.getAll();
            genres = genreService.getAll();
            countries = countryService.getAll();
            movieTypes = movieService.getAllMoviesTypes();
            persons = personService.getAll();
            request.setAttribute(RequestParameter.MOVIE_TYPES.getValue(), movieTypes);
            request.setAttribute(RequestParameter.GENRE_LIST.getValue(), genres);
            request.setAttribute(RequestParameter.COUNTRY_LIST.getValue(), countries);
            request.setAttribute(RequestParameter.POSITION_LIST.getValue(), positions);
            request.setAttribute(RequestParameter.PERSON_LIST.getValue(), persons);
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" got page for add movie");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.ADD_MOVIE_PAGE.getPath();
    }
}

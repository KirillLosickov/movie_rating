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
 * Class {@code ShowPageForEditMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class ShowPageForEditMovie implements ICommand {
    private static Logger logger = Logger.getLogger(ShowPageForEditMovie.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Call methods {@link IMovieService#getAllMoviesTypes()}, {@link ICountryService#getAll()},
     * {@link IGenreService#getAll()}, {@link IPositionService#getAll()},
     * {@link IPersonService#getAll()}, {@link IMovieService#getMovie(String)}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        JspPageName jspPageName = null;
        Movie movie = null;
        List<Genre> genreList = null;
        List<Country> countryList = null;
        List<Person> personList = null;
        List<Position> positionList = null;
        String idMovie = request.getParameter(RequestParameter.ID_MOVIE.getValue());
        List<Movie.TypeMovie> movieTypesList = null;
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to get page for edit movie");
        try {
            IMovieService movieService = serviceFactory.getMovieServiceImpl();
            ICountryService countryService = serviceFactory.getCountryServiceImpl();
            IGenreService genreService = serviceFactory.getGenreServiceImpl();
            IPositionService positionService = serviceFactory.getPositionService();
            IPersonService personService = serviceFactory.getPersonServiceImpl();
            positionList = positionService.getAll();
            genreList = genreService.getAll();
            countryList = countryService.getAll();
            movieTypesList = movieService.getAllMoviesTypes();
            personList = personService.getAll();
            movie = movieService.getMovie(idMovie);
            request.setAttribute(RequestParameter.GENRE_LIST.getValue(), genreList);
            request.setAttribute(RequestParameter.COUNTRY_LIST.getValue(), countryList);
            request.setAttribute(RequestParameter.MOVIE.getValue(), movie);
            request.setAttribute(RequestParameter.PERSON_LIST.getValue(), personList);
            request.setAttribute(RequestParameter.POSITION_LIST.getValue(), positionList);
            request.setAttribute(RequestParameter.MOVIE_TYPES.getValue(), movieTypesList);
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" got page for edit movie");
        } catch (ServiceException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        return JspPageName.EDIT_MOVIE_PAGE.getPath();
    }
}

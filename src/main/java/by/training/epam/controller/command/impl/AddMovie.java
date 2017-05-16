package by.training.epam.controller.command.impl;

import by.training.epam.controller.command.ICommand;
import by.training.epam.controller.servlet.JspPageName;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.service.IMovieService;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;
import by.training.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;


/**
 * Class {@code AddMovie} is the class which responsible for reception parameters from {@link HttpServletRequest}
 * and generate attributes for it.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public class AddMovie implements ICommand {
    private static Logger logger = Logger.getLogger(AddMovie.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    /**
     * Override method {@link ICommand#execute(HttpServletRequest, HttpServletResponse)}
     * Redirect parameters to {@link IMovieService#addMovie(String, String, String, String, String, String, String, String[], String[], String[], String[], String, Part, String)}
     * @return path of jsp page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" try to add film");
        IMovieService IMovieService = serviceFactory.getMovieServiceImpl();
        try {
            String title = request.getParameter(RequestParameter.TITLE.getValue());
            String budgetString = request.getParameter(RequestParameter.BUDGET.getValue());
            String profitString = request.getParameter(RequestParameter.PROFIT.getValue());
            String durationString = request.getParameter(RequestParameter.DURATION.getValue());
            String restrictionAgeString = request.getParameter(RequestParameter.RESTRICTION_AGE.getValue());
            String dateReleaseString = request.getParameter(RequestParameter.DATE_RELEASE.getValue());
            String type = request.getParameter(RequestParameter.TYPE.getValue());
            String[] countriesID = request.getParameterValues(RequestParameter.COUNTRY.getValue());
            String[] personsID = request.getParameterValues(RequestParameter.PERSON.getValue());
            String[] positionsID = request.getParameterValues(RequestParameter.POSITION.getValue());
            String[] genresID = request.getParameterValues(RequestParameter.GENRE.getValue());
            String descriptionString = request.getParameter(RequestParameter.DESCRIPTION.getValue());
            Part filePart = request.getPart(RequestParameter.POSTER.getValue());
            String webInfPath = request.getServletContext().getRealPath("/");
            IMovieService.addMovie(title, budgetString, profitString, durationString,
                    restrictionAgeString, dateReleaseString, type,
                    countriesID, personsID, positionsID, genresID, descriptionString, filePart, webInfPath);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), "Film is added");
            logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" added film");
        } catch (ServiceException | IOException | ServiceLogicException | ServletException e) {
            logger.error("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\"", e);
            request.setAttribute(RequestParameter.INFORMATION.getValue(), e.getCause().getMessage());
        }
        logger.debug("\"" + ((String) request.getSession().getAttribute(RequestParameter.LOGIN.getValue())) + "\" added film");
        return JspPageName.INDEX_PAGE.getPath();
    }
}
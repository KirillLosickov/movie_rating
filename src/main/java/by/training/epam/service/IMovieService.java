package by.training.epam.service;

import by.training.epam.bean.Comment;
import by.training.epam.bean.Movie;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;

import javax.servlet.http.Part;
import java.util.List;

/**
 * Simple movie operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IMovieService {

    /**
     * Add movie.
     * @param title the operand to use as title of movie.
     * @param budgetString the operand to use as budget of movie.
     * @param profitString the operand to use as profit of movie.
     * @param durationString the operand to use as duration of movie.
     * @param restrictionAgeString the operand to use as restriction age of movie.
     * @param dateReleaseString operand to use as release date of movie.
     * @param type the operand to use as type of movie.
     * @param countriesID the operand to use as countries's ID of movie.
     * @param personsID the operand to use as persons's ID of movie.
     * @param positionsID the operand to use as positions's ID of movie.
     * @param genresID the operand to use as genres's ID of movie.
     * @param genresID the operand to use as genres's ID of movie.
     * @param descriptionString the operand to use as description of movie.
     * @param part the operand to use for get image of movie.
     * @param webInfPath the operand to use as title of movie.
     * @throws by.training.epam.service.exception.ServiceException
     */
    void addMovie(String title, String budgetString, String profitString, String durationString,
                  String restrictionAgeString, String dateReleaseString, String type, String[] countriesID,
                  String personsID[], String[] positionsID, String[] genresID, String descriptionString,
                  Part part, String webInfPath) throws ServiceException, ServiceLogicException;

    /**
     * Edit movie.
     * @param idMovie the operand to use as ID of movie.
     * @param title the operand to use as title of movie.
     * @param budgetString the operand to use as budget of movie.
     * @param profitString the operand to use as profit of movie.
     * @param durationString the operand to use as duration of movie.
     * @param restrictionAgeString the operand to use as restriction age of movie.
     * @param dateReleaseString operand to use as release date of movie.
     * @param type the operand to use as type of movie.
     * @param countriesID the operand to use as countries's ID of movie.
     * @param personsID the operand to use as persons's ID of movie.
     * @param positionsID the operand to use as positions's ID of movie.
     * @param genresID the operand to use as genres's ID of movie.
     * @param genresID the operand to use as genres's ID of movie.
     * @param descriptionString the operand to use as description of movie.
     * @param part the operand to use for get image of movie.
     * @param webInfPath the operand to use as title of movie.
     * @throws by.training.epam.service.exception.ServiceException
     */
    void editMovie(String idMovie, String title, String budgetString, String profitString,
                   String durationString, String restrictionAgeString, String dateReleaseString,
                   String type, String[] countriesID, String personsID[], String[] positionsID,
                   String[] genresID, String descriptionString, Part part, String webInfPath)
            throws ServiceException, ServiceLogicException;

    /**
     * Set movie as removed
     * @param idMovie the operand to use as ID of movie.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    void setRemovedMovie(String idMovie) throws ServiceException;

    /**
     * Set movie as unremoved
     * @param idMovie the operand to use as ID of movie.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    void setUnRemovedMovie(String idMovie) throws ServiceException;

    /**
     * Set movie as removed
     * @param idMovie the operand to use as ID of movie.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    void addStatistic(String loginUser, String idMovie, String comment, String mark) throws ServiceException, ServiceLogicException;
    /**
     * Remove comment of movie
     * @param idComment the operand to use as ID of comment.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    void removeComment(String idComment) throws ServiceException;

    /**
     * Remove comment of movie
     * @param noOfRecords operand to use as amount of movies's records.
     * @return the amount of page.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    int getNoOfPages(int noOfRecords) throws ServiceException;

    /**
     * Get movie
     * @param idMovie to use as ID of movie.
     * @return the movie.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    Movie getMovie(String idMovie) throws ServiceException;

    /**
     * Get movie's comments
     * @param idMovie to use as ID of movie.
     * @return the list of comments.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    List<Comment> getCommentsByMovie(String idMovie) throws ServiceException;

    /**
     * Get movie's types
     * @return the list of movie's types.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    List<Movie.TypeMovie> getAllMoviesTypes() throws ServiceException;

    /**
     * Get amount of records
     * @param position to use as user's position.
     * @return the result of records.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    int getNoOfRecords(String position) throws ServiceException;

    /**
     * Sort movies by date added.
     * @param page to use as number of request page.
     * @param position to use as user's position.
     * @return the sorted list of movies by date.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    List<Movie> sortByDate(int page, String position) throws ServiceException;

    /**
     * Get number of page
     * @return the list of movie's types.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    int getPage(String page) throws ServiceException;

    /**
     * Sort movies by date rating
     * @param page to use as number of request page.
     * @param positionString to use as user's position.
     * @return the sorted list of movies by rating.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    List<Movie> sortByRating(int page, String positionString) throws ServiceException;

    /**
     * Get movies
     * @param page to use as number of request page.
     * @param positionString to use as user's position.
     * @return the list of movies.
     * @throws by.training.epam.service.exception.ServiceException
     * */
    List<Movie> getMovies(int page, String positionString) throws ServiceException;
}

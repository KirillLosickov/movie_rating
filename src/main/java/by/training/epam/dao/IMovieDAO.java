package by.training.epam.dao;

import by.training.epam.bean.Movie;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple movie operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IMovieDAO {

    /**
     * Add comment
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @return  the list of movies.
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Movie> getUnDeletedMovies(int page, int recordsOnPage) throws DAOException;

    /**
     * Get movies
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @throws by.training.epam.dao.exception.DAOException
     * @return  the list of movies.
     */
    List<Movie> getMovies(int page, int recordsOnPage) throws DAOException;

    /**
     * Get movie
     * @param idMovie the operand to use as ID of movie.
     * @return  the movies.
     * @throws by.training.epam.dao.exception.DAOException
     */
    Movie getMovie(int idMovie) throws DAOException;

    /**
     * Edit movie
     * @param movie the operand to use as movie.
     * @throws by.training.epam.dao.exception.DAOException
     */
    void editMovie(Movie movie) throws DAOException;

    /**
     * Get amount od movie's records
     * @return amount of movie's records.
     * @throws by.training.epam.dao.exception.DAOException
     */
    int getNoOfRecords() throws DAOException;

    /**
     * Set amount of unremoved records
     * @return amount of movie's records.
     * @throws by.training.epam.dao.exception.DAOException
     */
    int getNoOfUnDeletedRecords() throws DAOException;

    /**
     * Add comment
     * @param movie the operand to use as login of user.
     * @throws by.training.epam.dao.exception.DAOException
     */
    void addMovie(Movie movie) throws DAOException;

    /**
     * Set movie as removed
     * @param idMovie the operand to use as ID of movie.
     * @throws by.training.epam.dao.exception.DAOException
     */
    void setRemovedMovie(int idMovie) throws DAOException;

    /**
     * Set movie as unremoved
     * @param idMovie the operand to use as ID of movie.
     * @throws by.training.epam.dao.exception.DAOException
     */
    void setUnRemovedMovie(int idMovie) throws DAOException;

    /**
     * Sort unremoved movies by date
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @throws by.training.epam.dao.exception.DAOException
     * @return list of movies
     */
    List<Movie> sortUnRemovedByDate(int page, int recordsOnPage) throws DAOException;

    /**
     * Srot movie by date
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @throws by.training.epam.dao.exception.DAOException
     * @return list of movies
     */
    List<Movie> sortAllByDate(int page, int recordsOnPage) throws DAOException;

    /**
     * Sort movies by rating
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @return list of movies
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Movie> sortAllByRating(int page, int recordsOnPage) throws DAOException;

    /**
     * Sort unremoved movies by rating
     * @param page the operand to use as required page.
     * @param recordsOnPage the operand to use as amount of orders.
     * @return list of movies
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Movie> sortUnRemovedByRating(int page, int recordsOnPage) throws DAOException;

    /**
     * Get movie's types
     * @return list of movie's types
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Movie.TypeMovie> getAllMoviesTypes() throws DAOException;
}

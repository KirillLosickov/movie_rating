package by.training.epam.dao;

import by.training.epam.dao.exception.DAOException;

/**
 * Simple mark operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IMarkDAO {
    /**
     * Add mark to movie.
     * @param loginUser the operand to use as login of user.
     * @param idMovie the operand to use as ID of movie.
     * @param valueMark the operand to use as value of mark.
     * @throws DAOException
     */
    void addMark(String loginUser, int idMovie, Integer valueMark) throws DAOException;
}

package by.training.epam.dao;

import by.training.epam.bean.Comment;
import by.training.epam.dao.exception.DAOException;

import java.util.List;

/**
 * Simple comment operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface ICommentDAO {
    /**
     * Get all comments.
     * @param idMovie the operand to use as ID of movie.
     * @return the list of comments.
     * @throws by.training.epam.dao.exception.DAOException
     */
    List<Comment> getAllComments(int idMovie) throws DAOException;
    /**
     * Remove comment.
     * @param idComment the operand to use as ID of comment.
     * @exception by.training.epam.dao.exception.DAOException
     */
    void removeComment(int idComment) throws DAOException;
    /**
     * Add comment
     * @param loginUser the operand to use as login of user.
     * @param idMovie the operand to use as ID of movie.
     * @param comment the operand to use as value of comment.
     * @exception by.training.epam.dao.exception.DAOException
     */
    void addComment(String loginUser, int idMovie, String comment) throws DAOException;
}

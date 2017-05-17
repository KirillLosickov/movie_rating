package by.training.epam.dao;

import by.training.epam.bean.User;
import by.training.epam.dao.exception.DAOException;

import java.util.List;


/**
 * Simple user operation.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IUserDAO {
    /**
     * User's sign in
     * @param user the operand to use as user.
     * @throws DAOException
     */
    void signIn(User user) throws DAOException;

    /**
     * User's sign up
     * @param user the operand to use as user.
     * @return the list of countries.
     * @throws DAOException
     */
    void signUp(User user) throws DAOException;

    /**
     * Get all users.
     * @return the list of users.
     * @throws DAOException
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Set new user's locale.
     * @param loginUser the operand to use as user's login.
     * @param local the operand to use as user's locale.
     * @throws DAOException
     */
    void updateLocal(String loginUser, String local) throws DAOException;

    /**
     * Find user.
     * @param loginUser the operand to use as user's login.
     * @return the user.
     * @throws DAOException
     */
    User findUser(String loginUser) throws DAOException;

    /**
     * Set blocked user.
     * @param idUser the operand to use as user's ID.
     * @throws DAOException
     */
    void blockUser(int idUser) throws DAOException;

    /**
     * Set ubblocked user.
     * @param idUser the operand to use as user's ID.
     * @throws DAOException
     */
    void unblockUser(int idUser) throws DAOException;

    /**
     * Set user right as admin.
     * @param idUser the operand to use as user's ID.
     * @throws DAOException
     */
    void setAdminRight(int idUser) throws DAOException;

    /**
     * Set user right as simple user.
     * @param idUser the operand to use as user's ID.
     * @throws DAOException
     */
    void setUserRight(int idUser) throws DAOException;

    /**
     * Get user.
     * @param userLogin the operand to use as user's login.
     * @return the user.
     * @throws DAOException
     */
    User getUserByLogin(String userLogin) throws DAOException;

    /**
     * Change user.
     * @param user the operand to use as user.
     * @throws DAOException
     */
    void changeUser(User user) throws DAOException;

    /**
     * Get all counties.
     * @param idUser the operand to use as user's ID.
     * @param rating the operand to use as new rating of user.
     * @throws DAOException
     */
    void changeUserRating(int idUser, int rating) throws DAOException;
}

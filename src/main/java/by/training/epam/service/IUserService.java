package by.training.epam.service;

import by.training.epam.bean.User;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;

import java.util.List;

/**
 * Simple user operation.
 *
 * @author Sergei Kalashynski
 * @version 1.0
 */
public interface IUserService {

    /**
     * Set user as blocked
     * @param idUser the operand to use as ID of user.
     * @throws ServiceException
     * */
    void blockUser(String idUser) throws ServiceException;

    /**
     * Set user as unblocked
     * @param idUser the operand to use as ID of user.
     * @throws ServiceException
     * */
    void unblockUser(String idUser) throws ServiceException;

    /**
     * Update user's locale
     * @param userLogin the operand to use as login of user.
     * @param locale the operand to use as locale's value.
     * @throws ServiceException
     * */
    void updateLocale(String userLogin, String locale) throws ServiceException;

    /**
     * Return user
     * @param userLogin the operand to use as login of user.
     * @return the user.
     * @throws ServiceException
     * */
    User getUserByLogin(String userLogin) throws ServiceException;

    /**
     * Sign up
     * @param userLogin the operand to use as login of user.
     * @param userPassword the operand to use as password of user.
     * @param userEmail the operand to use as email of user.
     * @param userName the operand to use as name of user.
     * @param userLastName the operand to use as second name of user.
     * @param userLocale the operand to use as locale of user.
     * @return the user.
     * @throws ServiceException
     * */
    User signUp(String userLogin, String userPassword, String userEmail,
                String userName, String userLastName, String userLocale) throws ServiceException;

    /**
     *Sign in
     * @param userLogin the operand to use as login of user.
     * @param userPassword the operand to use as password of user.
     * @return the user.
     * @throws ServiceException
     * */
    User signIn(String userLogin, String userPassword) throws ServiceException;

    /**
     * Get all users
     * @return the list of users.
     * @throws ServiceException
     * */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Set user's rights as admin
     * @param idUser the operand to use as ID of user.
     * @throws ServiceException
     * */
    void setAdminRight(String idUser) throws ServiceException;

    /**
     * Set user's rights as simple user
     * @param idUser the operand to use as IDof user.
     * @throws ServiceException
     * */
    void setUserRight(String idUser) throws ServiceException;

    /**
     * Set user's rights as admin
     * @param idUser the operand to use as ID of user.
     * @param rating the operand to use as rating of user.
     * @throws ServiceException
     * */
    void changeUserRating(String idUser, String rating) throws ServiceException, ServiceLogicException;
}

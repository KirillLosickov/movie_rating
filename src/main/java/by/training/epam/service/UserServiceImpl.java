package by.training.epam.service;

import by.training.epam.bean.User;
import by.training.epam.dao.IUserDAO;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;
import by.training.epam.service.util.Util;
import by.training.epam.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements IUserService {
    private static final int MIN_RATING = 0;
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void blockUser(String idUser) throws ServiceException {
        logger.debug("UserServiceImpl.blockUser()");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(idUser);
            Util.isEmptyString(idUser);
            iUserDAO.blockUser(Integer.parseInt(idUser));
        } catch (NumberFormatException e) {
            throw new ServiceException("ошибка ввода, ожидается число", e);
        } catch (DAOException |UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.blockUser() - success");
    }

    @Override
    public void unblockUser(String idUser) throws ServiceException {
        logger.debug("UserServiceImpl.unblockUser() - success");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(idUser);
            Util.isEmptyString(idUser);
            iUserDAO.unblockUser(Integer.parseInt(idUser));
        } catch (NumberFormatException e) {
            throw new ServiceException("ошибка парсинга", e);
        } catch (DAOException |UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.unblockUser() - success");
    }

    @Override
    public void updateLocale(String userLogin, String locale) throws ServiceException {
        logger.debug("UserServiceImpl.updateLocale()");
        try {
            Util.isNull(userLogin, locale);
            Util.isEmptyString(userLogin, locale);
            IUserDAO iUserDAO = daoFactory.getIUserDAO();
            iUserDAO.updateLocal(userLogin, locale);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.updateLocale() - success");
    }

    @Override
    public User getUserByLogin(String userLogin) throws ServiceException {
        logger.debug("UserServiceImpl.getUserByLogin()");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        User user = null;
        try {
            user = iUserDAO.findUser(userLogin);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.getUserByLogin() - success");
        return user;
    }

    @Override
    public User signUp(String userLogin, String userPassword, String userEmail,
                       String userName, String userLastName, String userLocale) throws ServiceException {
        logger.debug("UserServiceImpl.signUp()");
        User user = null;
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(userLogin, userPassword, userEmail, userName, userLastName, userLocale);
            Util.isEmptyString(userLogin, userPassword, userEmail, userName, userLastName, userLocale);
            Util.matchCorrectString(userName, userLastName);
            Util.matchEmail(userEmail);
            user = new User(userLogin, userPassword, userEmail, userName, userLastName, userLocale);
            iUserDAO.signUp(user);
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.signUp()");
        return user;
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        logger.debug("UserServiceImpl.getAllUsers()");
        List<User> users = null;
        try {
            users = daoFactory.getIUserDAO().getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.getAllUsers() - success");
        return users;
    }

    @Override
    public User signIn(String userLogin, String userPassword) throws ServiceException {
        logger.debug("UserServiceImpl.signIn()");
        User user = null;
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(userLogin, userPassword);
            Util.isEmptyString(userLogin, userPassword);
            user = new User(userLogin, userPassword);
            iUserDAO.signIn(user);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.signIn() - success");
        return user;
    }

    @Override
    public void setAdminRight(String idUser) throws ServiceException {
        logger.debug("UserServiceImpl.setAdminRight()");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(idUser);
            Util.isEmptyString(idUser);
            iUserDAO.setAdminRight(Integer.parseInt(idUser));
        } catch (NumberFormatException e) {
            throw new ServiceException("ошибка ввода, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.setAdminRight() - success");
    }

    @Override
    public void setUserRight(String idUser) throws ServiceException {
        logger.debug("UserServiceImpl.setUserRight()");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            Util.isNull(idUser);
            Util.isEmptyString(idUser);
            iUserDAO.setUserRight(Integer.parseInt(idUser));
        } catch (NumberFormatException e) {
            throw new ServiceException("ошибка ввода, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.setUserRight() - success");
    }

    @Override
    public void changeUserRating(String idUserString, String ratingString) throws ServiceException, ServiceLogicException {
        logger.debug("UserServiceImpl.changeUserRating()");
        try {
            Util.isNull(idUserString, ratingString);
            Util.isEmptyString(idUserString, ratingString);
            int rating = Integer.parseInt(ratingString);
            if (rating < MIN_RATING) {
                throw new ServiceLogicException("рейтинг не может бать отрицательным");
            }
            daoFactory.getIUserDAO().changeUserRating(Integer.parseInt(idUserString), Integer.parseInt(ratingString));
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.changeUserRating() - success");
    }
}

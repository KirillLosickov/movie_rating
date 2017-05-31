package by.training.epam.dao.impl;

import by.training.epam.bean.User;
import by.training.epam.dao.IUserDAO;
import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.pool.impl.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    private static Logger logger = Logger.getLogger(UserDAOImpl.class);

    private static final String SIGN_IN = "SELECT `u_id`,`u_login`,`u_password`,`u_name`,`u_lastname`,`u_email`," +
            "`u_position`,`u_rating`,`u_isblock`,`u_local` FROM mydb.users WHERE `u_login`=? and `u_password`=?";
    private static final String SIGN_UP = "INSERT INTO `mydb`.`users` (`u_login`, `u_password`, `u_name`," +
            " `u_lastname`,`u_email`, `u_local`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SHOW_ALL_USERS = "SELECT `u_id`,`u_login`,`u_password`,`u_name`,`u_lastname`," +
            "`u_email`,`u_position`,`u_rating`,`u_isblock`,`u_local` FROM mydb.users";
    private static final String UPDATE_LOCAL = "UPDATE `mydb`.`users` SET `u_local`=? WHERE `u_login`=?";
    private static final String GET_POSITION = "SELECT `u_position` FROM mydb.users WHERE `u_login`=?";
    private static final String FIND_USER_BY_LOGIN = "SELECT `u_id`,`u_login`,`u_password`,`u_name`,`u_lastname`," +
            "`u_email`,`u_position`,`u_rating`,`u_isblock`,`u_local` FROM mydb.users WHERE `u_login`=?";
    private static final String BLOCK_USER = "UPDATE `mydb`.`users` SET `u_isblock`='1' WHERE `u_id`=?";
    private static final String UNBLOCK_USER = "UPDATE `mydb`.`users` SET `u_isblock`='0' WHERE `u_id`=?";
    private static final String SET_ADMIN_RIGHT = "UPDATE `mydb`.`users` SET `u_position`='admin' WHERE `u_id`=?";
    private static final String SET_USER_RIGHT = "UPDATE `mydb`.`users` SET `u_position`='user' WHERE `u_id`=?";
    private static final String GET_USER_BY_LOGIN = "SELECT `u_id`,`u_login`,`u_password`,`u_name`,`u_lastname`,`u_email`," +
            "`u_position`,`u_rating`,`u_isblock`,`u_local` FROM mydb.users WHERE `u_login`=?";
    private static final String CHANGE_USER = "UPDATE `mydb`.`users` SET `u_login`=?, `u_password`=?, `u_name`=?, `u_email`=?, `u_lastname`=?, `u_position`=?, `u_rating`=?, `u_isblock`=?, `u_local`=? WHERE `u_id`=?";
    private static final String CHANGE_USER_RATING = "UPDATE `mydb`.`users` SET `u_rating`=? WHERE `u_id`=?";

    @Override
    public void signIn(User user) throws DAOException {
        logger.debug("UserDAOImpl.signIn()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SIGN_IN);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                setUserParameters(user, resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (error of sign in)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("UserDAOImpl.signIn() - success");
    }

    @Override
    public void signUp(User user) throws DAOException {
        logger.debug("UserDAOImpl.signUp()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SIGN_UP);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getLocal());
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (registration)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("UserDAOImpl.signUp() - success");
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        logger.debug("UserDAOImpl.getAllUsers()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SHOW_ALL_USERS);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                setUserParameters(user, resultSet);
                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (get users)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("UserDAOImpl.getAllUsers() - success");
        return users;
    }

    @Override
    public void updateLocal(String login, String local) throws DAOException {
        logger.debug("UserDAOImpl.updateLocal()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(UPDATE_LOCAL);
            ps.setString(2, login);
            ps.setString(1, local);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (update locale)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("UserDAOImpl.updateLocal() - success");
    }

    @Override
    public User findUser(String loginUser) throws DAOException {
        logger.debug("UserDAOImpl.findUser()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(FIND_USER_BY_LOGIN);
            ps.setString(1, loginUser);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                setUserParameters(user, resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (find user)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("UserDAOImpl.findUser() - success");
        return user;
    }

    private void setUserParameters(User user, ResultSet resultSet) throws SQLException {
        logger.debug("UserDAOImpl.setUserParameters()");
        user.setId(resultSet.getInt(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setName(resultSet.getString(4));
        user.setLastName(resultSet.getString(5));
        user.setEmail(resultSet.getString(6));
        user.setPosition(resultSet.getString(7));
        user.setRating(resultSet.getInt(8));
        user.setBlock(resultSet.getBoolean(9));
        user.setLocal(resultSet.getString(10));
        logger.debug("UserDAOImpl.updateLocal() - success");
    }

    @Override
    public void blockUser(int idUser) throws DAOException {
        logger.debug("UserDAOImpl.blockUser()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(BLOCK_USER);
            ps.setInt(1, idUser);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (block user)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.blockUser() - success");
    }

    @Override
    public void unblockUser(int idUser) throws DAOException {
        logger.debug("UserDAOImpl.blockUser() - success");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(UNBLOCK_USER);
            ps.setInt(1, idUser);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (unblock user)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.blockUser() - success");
    }

    @Override
    public void setAdminRight(int idUser) throws DAOException {
        logger.debug("UserDAOImpl.setAdminRight()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SET_ADMIN_RIGHT);
            ps.setInt(1, idUser);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database ( set admin right)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.setAdminRight() - success");
    }

    @Override
    public void setUserRight(int idUser) throws DAOException {
        logger.debug("UserDAOImpl.setUserRight()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SET_USER_RIGHT);
            ps.setInt(1, idUser);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (set user right)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.setUserRight() - success");
    }

    @Override
    public User getUserByLogin(String userLogin) throws DAOException {
        logger.debug("UserDAOImpl.getUserByLogin()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN);
            ps.setString(1, userLogin);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                setUserParameters(user, resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (get user by login)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.getUserByLogin() - success");
        return user;
    }

    @Override
    public void changeUser(User user) throws DAOException {
        logger.debug("UserDAOImpl.changeUser()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(CHANGE_USER);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPosition());
            ps.setInt(7, user.getRating());
            ps.setBoolean(8, user.isBlock());
            ps.setString(9, user.getLocal());
            ps.setInt(10, user.getId());
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (change user)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.changeUser() - success");
    }

    @Override
    public void changeUserRating(int idUser,int rating) throws DAOException {
        logger.debug("UserDAOImpl.changeUserRating()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(CHANGE_USER_RATING);
            ps.setInt(1, rating);
            ps.setInt(2, idUser);
            ps.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database (change user's rating )", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("UserDAOImpl.changeUserRating() - success");
    }
}

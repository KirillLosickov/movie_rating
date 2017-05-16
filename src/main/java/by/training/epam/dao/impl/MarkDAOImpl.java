package by.training.epam.dao.impl;

import by.training.epam.dao.IMarkDAO;
import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MarkDAOImpl implements IMarkDAO {
    private static final String ADD_MARK = "INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`," +
            " `mark`) VALUES ((select `u_id` from `users` where `u_login`=?), ?, ?)";

    private static Logger logger = Logger.getLogger(MarkDAOImpl.class);
    @Override
    public void addMark(String loginUser, int idMovie,  Integer valueMark) throws DAOException {
        logger.debug("MarkDAOImpl.addMark()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(ADD_MARK);
            ps.setString(1, loginUser);
            ps.setInt(2, idMovie);
            if (valueMark == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, valueMark);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (добавление оценки к фильму)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("MarkDAOImpl.addMark() - success");
    }
}

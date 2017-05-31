package by.training.epam.dao.impl;

import by.training.epam.bean.Genre;
import by.training.epam.dao.IGenreDAO;
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

public class GenreDAOImpl implements IGenreDAO {
    private static final String GET_ALL_GENRES = "SELECT `g_id`,`g_name` FROM mydb.genres";

    private static Logger logger = Logger.getLogger(GenreDAOImpl.class);
    private void setGenreParameters(Genre genre, ResultSet resultSet) throws SQLException {
        genre.setId(resultSet.getInt(1));
        genre.setName(resultSet.getString(2));
    }

    @Override
    public List<Genre> getAll() throws DAOException {
        logger.debug("GenreDAOImpl.getAll()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Genre> countries = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_ALL_GENRES);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                setGenreParameters(genre, resultSet);
                countries.add(genre);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка запроса (получение всех жанров)");
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("GenreDAOImpl.getAll() - success");
        return countries;
    }

}

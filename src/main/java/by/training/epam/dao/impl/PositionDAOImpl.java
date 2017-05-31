package by.training.epam.dao.impl;

import by.training.epam.bean.Position;
import by.training.epam.dao.IPosition;
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

public class PositionDAOImpl implements IPosition {
    private static final String GET_ALL_POSITIONS = "SELECT `p_id`,`p_type` FROM mydb.positions";
    private static Logger logger = Logger.getLogger(PositionDAOImpl.class);

    @Override
    public List<Position> getAll() throws DAOException {
        logger.debug("PositionDAOImpl.getAll()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Position> countries = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_ALL_POSITIONS);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Position position = new Position();
                setPositionParameters(position, resultSet);
                countries.add(position);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (получение всех должностей)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("PositionDAOImpl.getAll() - success");
        return countries;
    }

    private void setPositionParameters(Position position, ResultSet resultSet) throws SQLException {
        logger.debug("PositionDAOImpl.setPositionParameters()");
        position.setId(resultSet.getInt(1));
        position.setValue(resultSet.getString(2));
        logger.debug("PositionDAOImpl.setPositionParameters() - success");
    }
}

package by.training.epam.dao.impl;

import by.training.epam.bean.Country;
import by.training.epam.dao.ICountryDAO;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl implements ICountryDAO {
    private static final String GET_ALL_COUNTRIES = "SELECT `c_id`,`c_name` FROM mydb.countries";

    private static Logger logger = Logger.getLogger(CountryDAOImpl.class);

    @Override
    public List<Country> getAll() throws DAOException {
        logger.debug("CountryDAOImpl.getAll()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Country> countries = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_ALL_COUNTRIES);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                setCountryParameters(country, resultSet);
                countries.add(country);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка запроса (получение всех стран)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("CountryDAOImpl.getAll() - success");
        return countries;
    }

    private void setCountryParameters(Country country, ResultSet resultSet) throws SQLException {
        logger.debug("CountryDAOImpl.setCountryParameters()");
        country.setId(resultSet.getInt(1));
        country.setName(resultSet.getString(2));
        logger.debug("CountryDAOImpl.setCountryParameters() - success");
    }
}

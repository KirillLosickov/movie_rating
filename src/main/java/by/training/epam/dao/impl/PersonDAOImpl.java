package by.training.epam.dao.impl;

import by.training.epam.bean.Person;
import by.training.epam.dao.IPerson;
import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements IPerson {
    private static final String GET_ALL_PERSONS = "SELECT `p_id`,`p_name`,`p_lastname` FROM mydb.persons";
    private static Logger logger = Logger.getLogger(PersonDAOImpl.class);

    @Override
    public List<Person> getAll() throws DAOException {
        logger.debug("PersonDAOImpl.getAll()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Person> persons = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_ALL_PERSONS);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                setPersonParameters(person, resultSet);
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполненя запроса (получение всех person) ", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("PersonDAOImpl.getAll() - success");
        return persons;
    }

    private void setPersonParameters(Person person, ResultSet resultSet) throws SQLException {
        logger.debug("PersonDAOImpl.setPersonParameters()");
        person.setId(resultSet.getInt(1));
        person.setName(resultSet.getString(2));
        person.setLastname(resultSet.getString(3));
        logger.debug("PersonDAOImpl.setPersonParameters() - success");
    }
}

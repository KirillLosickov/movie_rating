package by.training.epam.dao.pool.impl;

import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.pool.DBParametr;
import by.training.epam.config.DBResourceManager;
import by.training.epam.dao.pool.ICloseConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.MissingResourceException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool implements ICloseConnectionPool {
    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;


    private ConnectionPool() throws ConnectionPoolException {
        try {
            DBResourceManager dbResourceManager = DBResourceManager.getInstance();
            logger.info("System found database property file");
            this.driverName = dbResourceManager.getValue(DBParametr.DB_DRIVER);
            this.user = dbResourceManager.getValue(DBParametr.DB_USER);
            this.url = dbResourceManager.getValue(DBParametr.DB_URL);
            this.password = dbResourceManager.getValue(DBParametr.DB_PASSWORD);
            this.poolSize = Integer.parseInt(dbResourceManager.getValue((DBParametr.DN_POOL_SIZE)));
            Class.forName(driverName);
            logger.info("Database driver was loaded");
        } catch (NumberFormatException e) {
            logger.warn("No correct value in database property file");
            poolSize = 5;
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver load exception: " + driverName, e);
            throw new ConnectionPoolException("Driver load exception: " + driverName, e);
        } catch (MissingResourceException e) {
            logger.fatal("Error of upload config: " + e);
            throw new ConnectionPoolException("Error of upload config: " + e);
        }
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionQueue.add(getConnection());
        }
    }

    private Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Get connection from database");
        } catch (SQLException e) {
            logger.warn("Can't get connection from database", e);
            throw new ConnectionPoolException("не возможно выдать соединение к БД", e);
        }
        return connection;
    }

    public Connection retrieve() throws ConnectionPoolException {//извлечь
        Connection connection = null;
        if (connectionQueue.size() == 0) {
            connection = getConnection();
        } else {
            try {
                connection = connectionQueue.take();
            } catch (InterruptedException e) {
                throw new ConnectionPoolException("не возможно выдать соединение к БД", e);
            }

        }
        givenAwayConQueue.add(connection);
        return connection;
    }

    private void putBack(Connection connection) throws NullPointerException {//вернуть
        if (connection != null) {
            givenAwayConQueue.remove(connection);
            connectionQueue.add(connection);
        } else {
            throw new NullPointerException("Connection is null");
        }
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {//Высокая производительность
        ConnectionPool localInstance = instance;
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    //не выбрасываю исключение на верхний слой, т.к. считаю, что нужно
    // попытаться заврать все объекты в данных методах
    public void putBackConnection(Connection con, Statement st, ResultSet resultSet) {
        try {
            this.putBack(con);
        } catch (NullPointerException e) {
            //MyLogger
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                //logger
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //logger
            }
        }
    }

    public void putBackConnection(Connection con, Statement st) {
        this.putBackConnection(con, st, null);
    }

    @Override
    public void releasePool() {
        while (!givenAwayConQueue.isEmpty()) {
            try {
                Connection connection = givenAwayConQueue.take();
                connection.close();
            } catch (InterruptedException | SQLException e) {
                logger.error("Can't close connection" + e);
            }
        }
        while (!connectionQueue.isEmpty()) {
            try {
                Connection connection = connectionQueue.take();
                connection.close();
            } catch (InterruptedException | SQLException e) {
                logger.error("Can't close connection" + e);
            }
        }
    }
}

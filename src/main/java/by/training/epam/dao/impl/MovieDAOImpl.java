package by.training.epam.dao.impl;

import by.training.epam.bean.*;
import by.training.epam.dao.IMovieDAO;
import by.training.epam.dao.exception.ConnectionPoolException;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.pool.impl.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MovieDAOImpl implements IMovieDAO {
    private static final String SHOW_ACTUAL_MOVIES = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`,`movies`.`m_deleted`," +
            "`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`,avg(IFNULL(`mark`.`mark`, 0))" +
            " FROM `mydb`.`movies` left join `mydb`.`mark` on `movies`.`m_id`=`mark`.`movie_id` " +
            "WHERE `m_deleted`=FALSE GROUP BY `movies`.`m_id` desc LIMIT ?,?";
    private static final String SHOW_ALL_MOVIES = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`,`movies`.`m_deleted`," +
            "`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`,avg(`mark`.`mark`) " +
            "FROM `mydb`.`movies` left join `mydb`.`mark` on `movies`.`m_id`=`mark`.`movie_id`" +
            " GROUP BY `movies`.`m_id` desc LIMIT ?,?";
    private static final String GET_MOVIE = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`," +
            "`movies`.`m_deleted`,`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`," +
            "IFNULL(avg(`mark`.`mark`), 0) FROM `mydb`.`movies` left join `mydb`.`mark`" +
            " on `movies`.`m_id`=`mark`.`movie_id`  WHERE `movies`.`m_id`=?";

    private static final String SORT_BY_DATE = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`,`movies`.`m_deleted`," +
            "`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`, avg(`mark`.`mark`)" +
            " FROM `mydb`.`movies` left join `mydb`.`mark` on `movies`.`m_id`=`mark`.`movie_id`" +
            " GROUP BY `movies`.`m_id` ORDER BY `movies`.`m_release_date` DESC LIMIT ?,?";
    private static final String SORT_ACTUAL_BY_DATE = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`,`movies`.`m_deleted`," +
            "`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`, avg(`mark`.`mark`)" +
            " FROM `mydb`.`movies` left join `mydb`.`mark` on `movies`.`m_id`=`mark`.`movie_id`" +
            " WHERE `movies`.`m_deleted`=FALSE GROUP BY `movies`.`m_id` ORDER BY `movies`.`m_release_date` DESC LIMIT ?,?";

    private static final String SORT_BY_RATING = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`,`movies`.`m_deleted`," +
            "`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`, avg(`mark`.`mark`)" +
            " as avg_mark FROM `mydb`.`movies` left join `mydb`.`mark` on `movies`.`m_id`=`mark`.`movie_id` " +
            "GROUP BY `movies`.`m_id` ORDER BY avg_mark DESC LIMIT ?,?";
    private static final String SORT_ACTUAL_BY_RATING = "SELECT `movies`.`m_id`,`movies`.`m_title`,`movies`.`m_release_date`," +
            "`movies`.`m_type`,`movies`.`m_age`,`movies`.`m_image_name`,`movies`.`m_duration`," +
            "`movies`.`m_deleted`,`movies`.`m_budget`,`movies`.`m_profit`,`movies`.`m_description`," +
            "avg(`mark`.`mark`) as avg_mark FROM `mydb`.`movies` join `mydb`.`mark` " +
            "on `movies`.m_id=`mark`.m_id WHERE `m_deleted`=FALSE GROUP BY `movies`.`movie_id` " +
            "ORDER BY (avg_mark) DESC LIMIT ?,?";

    private static final String GET_COUNT = "SELECT count(*) from `mydb`.`movies`";
    private static final String GET_UNDELETED_COUNT = "SELECT count(*) from `mydb`.`movies` where `m_deleted`=FALSE";
    private static final String ADD_MOVIE = "INSERT INTO `mydb`.`movies` (`m_title`, `m_release_date`, `m_type`," +
            " `m_age`,`m_image_name`, `m_duration`, `m_budget`, `m_profit`, `m_description`) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
    private static final String SET_DELETED_MOVIE = "UPDATE `mydb`.`movies` SET `m_deleted`='1' WHERE `m_id`=?";
    private static final String SET_UNDELETED_MOVIE = "UPDATE `mydb`.`movies` SET `m_deleted`='0' WHERE `m_id`=?";
    private static final String GET_LAST_ID = "SELECT `movies`.`m_id` FROM mydb.movies ORDER BY `movies`.`m_id` desc limit 1;";

    private static final String ADD_COUNTRIES_TO_MOVIE = "INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`," +
            "`movies_m_id`) VALUES (?, ?)";
    private static final String ADD_GENRES_TO_MOVIE = "INSERT INTO `mydb`.`m2m_movies_genres` (`genres_g_id` , `movies_m_id` )" +
            " VALUES (?, ?)";
    private static final String ADD_MEMBERS_TO_MOVIE = "INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, " +
            "`movies_m_id`) VALUES (?, ?, ?)";
    private static final String GET_COUNTRIES_TO_MOVIE = "SELECT `countries`.`c_id`,`countries`.`c_name` " +
            "FROM countries join m2m_countries_movies on" +
            "`countries`.`c_id`=`m2m_countries_movies`.countries_c_id and m2m_countries_movies.movies_m_id=?";
    private static final String GET_GENRES_TO_MOVIE = "SELECT `genres`.`g_id`,`genres`.`g_name` FROM genres " +
            "join m2m_movies_genres on `genres`.`g_id`=`m2m_movies_genres`.genres_g_id and m2m_movies_genres.movies_m_id=?";
    private static final String GET_MEMBERS_TO_MOVIE = "SELECT `persons`.`p_id`,`persons`.`p_name`,`persons`.`p_lastname`," +
            "`positions`.`p_id`, `positions`.`p_type` FROM mydb.members " +
            "join mydb.persons on members.persons_p_id=persons.p_id " +
            "join mydb.positions on members.positions_p_id=positions.p_id where members.movies_m_id=?";
    private static final String GET_ALL_TYPES_OF_MOVIES = "select distinct `movies`.`m_type` from movies";
    private static final String REMOVE_MOVIE = "DELETE FROM `mydb`.`movies` WHERE `m_id`=?";

    private static Logger logger = Logger.getLogger(MovieDAOImpl.class);
    private void setMovieParameters(Movie movie, ResultSet resultSet) throws SQLException {
        logger.debug("MovieDAOImpl.setMovieParameters()");
        movie.setId(resultSet.getInt(1));
        movie.setTitle(resultSet.getString(2));
        movie.setReleaseDate(resultSet.getDate(3));
        movie.setType(Movie.TypeMovie.valueOf(resultSet.getString(4).toUpperCase()));
        movie.setRestrictionAge(resultSet.getInt(5));
        movie.setImageName(resultSet.getString(6));
        movie.setDuration(resultSet.getTime(7));
        movie.setDeleted(resultSet.getBoolean(8));
        movie.setBudget(resultSet.getInt(9));
        movie.setProfit(resultSet.getInt(10));
        movie.setDescription(resultSet.getString(11));
        movie.setAverageMark(resultSet.getDouble(12));
        logger.debug("MovieDAOImpl.setMovieParameters() - success");
    }

    @Override
    public void editMovie(Movie movie) throws DAOException {
        logger.debug("MovieDAOImpl.editMovie()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(REMOVE_MOVIE);
            preparedStatement.setInt(1, movie.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(ADD_MOVIE);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setDate(2, new Date(movie.getReleaseDate().getTime()));
            preparedStatement.setString(3, movie.getType().toString().toLowerCase());
            preparedStatement.setInt(4, movie.getRestrictionAge());
            preparedStatement.setString(5, movie.getImageName());
            preparedStatement.setTime(6, new Time(movie.getDuration().getTime()));
            preparedStatement.setInt(7, movie.getBudget());
            preparedStatement.setInt(8, movie.getProfit());
            preparedStatement.setString(9, movie.getDescription());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(GET_LAST_ID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idMovie = resultSet.getInt(1);
                preparedStatement = connection.prepareStatement(ADD_COUNTRIES_TO_MOVIE);
                for (Country country : movie.getCountries()) {
                    preparedStatement.setInt(1, country.getId());
                    preparedStatement.setInt(2, idMovie);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connection.prepareStatement(ADD_GENRES_TO_MOVIE);
                for (Genre genre : movie.getGenres()) {
                    preparedStatement.setInt(1, genre.getId());
                    preparedStatement.setInt(2, idMovie);
                    preparedStatement.executeUpdate();
                }

                preparedStatement = connection.prepareStatement(ADD_MEMBERS_TO_MOVIE);

                for (Position position : movie.getMembers().keySet()) {
                    Set<Person> persons = movie.getMembers().get(position);
                    for (Person person : persons) {
                        preparedStatement.setInt(1, person.getId());
                        preparedStatement.setInt(2, position.getId());
                        preparedStatement.setInt(3, idMovie);
                        preparedStatement.executeUpdate();
                    }
                }
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.editMovie() - success");
    }

    @Override
    public void addMovie(Movie movie) throws DAOException {
        logger.debug("MovieDAOImpl.addMovie()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(ADD_MOVIE);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setDate(2, new Date(movie.getReleaseDate().getTime()));
            preparedStatement.setString(3, movie.getType().toString().toLowerCase());
            preparedStatement.setInt(4, movie.getRestrictionAge());
            preparedStatement.setString(5, movie.getImageName());
            preparedStatement.setTime(6, new Time(movie.getDuration().getTime()));
            preparedStatement.setInt(7, movie.getBudget());
            preparedStatement.setInt(8, movie.getProfit());
            preparedStatement.setString(9, movie.getDescription());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(GET_LAST_ID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idMovie = resultSet.getInt(1);
                preparedStatement = connection.prepareStatement(ADD_COUNTRIES_TO_MOVIE);
                for (Country country : movie.getCountries()) {
                    preparedStatement.setInt(1, country.getId());
                    preparedStatement.setInt(2, idMovie);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connection.prepareStatement(ADD_GENRES_TO_MOVIE);
                for (Genre genre : movie.getGenres()) {
                    preparedStatement.setInt(1, genre.getId());
                    preparedStatement.setInt(2, idMovie);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connection.prepareStatement(ADD_MEMBERS_TO_MOVIE);
                for (Position position : movie.getMembers().keySet()) {
                    Set<Person> persons = movie.getMembers().get(position);
                    for (Person person : persons) {
                        preparedStatement.setInt(1, person.getId());
                        preparedStatement.setInt(2, position.getId());
                        preparedStatement.setInt(3, idMovie);
                        preparedStatement.executeUpdate();
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException("ошибка запроса в БД(добавление фильма)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.addMovie() - success");
    }

    @Override
    public List<Movie> getUnDeletedMovies(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.getUnDeletedMovies()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SHOW_ACTUAL_MOVIES);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка запроса в БД(просмотр фильма)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getUnDeletedMovies() - success");
        return movies;
    }

    @Override
    public List<Movie> getMovies(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.getMovies()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SHOW_ALL_MOVIES);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка запроса в БД(чтение фильмов)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getMovies() - success");
        return movies;
    }

    @Override
    public Movie getMovie(int idMovie) throws DAOException {
        logger.debug("MovieDAOImpl.getMovie()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Movie movie = new Movie();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(GET_MOVIE);
            ps.setInt(1, idMovie);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                setMovieParameters(movie, resultSet);
            }

            ps = connection.prepareStatement(GET_COUNTRIES_TO_MOVIE);
            ps.setInt(1, idMovie);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                movie.addCountry(country);
            }
            ps = connection.prepareStatement(GET_GENRES_TO_MOVIE);
            ps.setInt(1, idMovie);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                movie.addGenre(genre);
            }

            ps = connection.prepareStatement(GET_MEMBERS_TO_MOVIE);
            ps.setInt(1, idMovie);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setLastname(resultSet.getString(3));
                Position position = new Position();
                position.setId(resultSet.getInt(4));
                position.setValue(resultSet.getString(5));
                movie.addPerson(position, person);
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException("ошибка выполнения запроса(поиск фильма)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getMovie() - success");
        return movie;
    }

    @Override
    public int getNoOfRecords() throws DAOException {
        logger.debug("MovieDAOImpl.getNoOfRecords()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_COUNT);
            resultSet = ps.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса(получение количества фильмов)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getNoOfRecords() - success");
        return result;
    }

    @Override
    public int getNoOfUnDeletedRecords() throws DAOException {
        logger.debug("MovieDAOImpl.getNoOfUnDeletedRecords()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_UNDELETED_COUNT);
            resultSet = ps.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса(получение количества неудалённых записей)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getNoOfUnDeletedRecords() - success");
        return result;
    }

    @Override
    public void setRemovedMovie(int idMovie) throws DAOException {
        logger.debug("MovieDAOImpl.setRemovedMovie()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(SET_DELETED_MOVIE);
            preparedStatement.setInt(1, idMovie);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (удаления фильма)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }
        logger.debug("MovieDAOImpl.setRemovedMovie() - success");
    }

    @Override
    public void setUnRemovedMovie(int idMovie) throws DAOException {
        logger.debug("MovieDAOImpl.setUnRemovedMovie()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(SET_UNDELETED_MOVIE);
            preparedStatement.setInt(1, idMovie);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (восстановления фильма)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, preparedStatement);
            }
        }
        logger.debug("MovieDAOImpl.setUnRemovedMovie() - success");
    }

    @Override
    public List<Movie> sortUnRemovedByDate(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.sortUnRemovedByDate()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SORT_ACTUAL_BY_DATE);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (сортировка по дате)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.sortUnRemovedByDate() - success");
        return movies;
    }

    @Override
    public List<Movie> sortAllByDate(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.sortAllByDate()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SORT_BY_DATE);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (сортировка актуальных записей по дате)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.sortAllByDate() - success");
        return movies;
    }

    @Override
    public List<Movie> sortAllByRating(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.sortAllByRating()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SORT_BY_RATING);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (сортировка по рейтингу)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.sortAllByRating() - success");
        return movies;
    }

    @Override
    public List<Movie> sortUnRemovedByRating(int offset, int noOfRecords) throws DAOException {
        logger.debug("MovieDAOImpl.sortUnRemovedByRating()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Movie> movies = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(SORT_ACTUAL_BY_RATING);
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (сортировка актуальных записей по рейтингу)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.sortUnRemovedByRating() - success");
        return movies;
    }

    @Override
    public List<Movie.TypeMovie> getAllMoviesTypes() throws DAOException {
        logger.debug("MovieDAOImpl.getAllMoviesTypes()");
        List<Movie.TypeMovie> list = new ArrayList<>();
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_ALL_TYPES_OF_MOVIES);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(Movie.TypeMovie.valueOf(resultSet.getString(1).toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (получение всех типов фильмов)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("MovieDAOImpl.getAllMoviesTypes() - success");
        return list;
    }
}

package by.training.epam.dao.impl;

import by.training.epam.bean.Comment;
import by.training.epam.dao.ICommentDAO;
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

public class CommentDAOImpl implements ICommentDAO {
    private static Logger logger = Logger.getLogger(CommentDAOImpl.class);
    private static final String GET_COMMENTS_BY_MOVIE = "SELECT `users`.`u_login`,`comment`.`c_id`, `comment`.`m_id`," +
            "`comment`.`comment` FROM mydb.users join mydb.comment on mydb.comment.u_id=mydb.users.u_id " +
            "where `comment`.`m_id`=? and `comment`.`comment` is not null order by `comment`.`c_id`";
    private static final String REMOVE_COMMENT = "DELETE FROM `mydb`.`comment` WHERE `c_id`=?";

    private static final String ADD_COMMENT = "INSERT INTO `mydb`.`comment` (`u_id`, `m_id`," +
            " `comment`) VALUES ((select `u_id` from `users` where `u_login`=?), ?, ?)";

    @Override
    public List<Comment> getAllComments(int idMovie) throws DAOException {
        logger.debug("CommentDAOImpl.getAllComments()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        List<Comment> comments = new ArrayList<>();
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(GET_COMMENTS_BY_MOVIE);
            ps.setInt(1, idMovie);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setUserLogin(resultSet.getString(1));
                comment.setId(2);
                comment.setIdMovie(resultSet.getInt(3));
                comment.setValue(resultSet.getString(4));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (получение комментариев к фильму)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps, resultSet);
            }
        }
        logger.debug("CommentDAOImpl.getAllComments() - success");
        return comments;
    }

    @Override
    public void removeComment(int idComment) throws DAOException {
        logger.debug("CommentDAOImpl.removeComment()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(REMOVE_COMMENT);
            ps.setInt(1,idComment);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (удаления комментария)", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("не возможно выдать соединение к БД", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("CommentDAOImpl.removeComment() - success");
    }

    public void addComment(String loginUser, int idMovie, String comment) throws DAOException {
        logger.debug("CommentDAOImpl.addComment()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            ps = connection.prepareStatement(ADD_COMMENT);
            ps.setString(1, loginUser);
            ps.setInt(2, idMovie);
            ps.setString(3, comment);
            ps.setString(3, comment);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ошибка выполнения запроса (добавление статистики к фильму)", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, ps);
            }
        }
        logger.debug("CommentDAOImpl.addComment() - success");
    }
}

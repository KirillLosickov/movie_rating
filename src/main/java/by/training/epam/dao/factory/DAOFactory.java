package by.training.epam.dao.factory;

import by.training.epam.dao.impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final UserDAOImpl iUserDAO = new UserDAOImpl();
    private final CommentDAOImpl iCommentDAO = new CommentDAOImpl();
    private final MovieDAOImpl iMovieDAO = new MovieDAOImpl();
    private final GenreDAOImpl iGenreDAO = new GenreDAOImpl();
    private final CountryDAOImpl iCountryDAO = new CountryDAOImpl();
    private final PersonDAOImpl iPerson = new PersonDAOImpl();
    private final PositionDAOImpl iPosition = new PositionDAOImpl();
    private final MarkDAOImpl iMarkDAO = new MarkDAOImpl();

    private DAOFactory() {

    }

    public MarkDAOImpl getIMarkDAO() {
        return iMarkDAO;
    }

    public CommentDAOImpl getICommentDAO() {
        return iCommentDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAOImpl getIUserDAO() {
        return iUserDAO;
    }

    public MovieDAOImpl getIMovieDAO() {
        return iMovieDAO;
    }

    public GenreDAOImpl getIGenreDAO() {
        return iGenreDAO;
    }

    public CountryDAOImpl getICountryDAO() {
        return iCountryDAO;
    }

    public PositionDAOImpl getIPosition() {
        return iPosition;
    }

    public PersonDAOImpl getIPerson() {
        return iPerson;
    }

}

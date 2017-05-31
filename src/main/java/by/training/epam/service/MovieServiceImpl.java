package by.training.epam.service;

import by.training.epam.bean.*;
import by.training.epam.controller.servlet.RequestParameter;
import by.training.epam.dao.ICommentDAO;
import by.training.epam.dao.IMarkDAO;
import by.training.epam.dao.IMovieDAO;
import by.training.epam.dao.IUserDAO;
import by.training.epam.dao.exception.DAOException;
import by.training.epam.dao.factory.DAOFactory;
import by.training.epam.config.ServiceResourceManager;
import by.training.epam.service.exception.ServiceException;
import by.training.epam.service.exception.ServiceLogicException;
import by.training.epam.service.util.Util;
import by.training.epam.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static by.training.epam.controller.servlet.RequestParameter.ADMIN;
import static by.training.epam.controller.servlet.RequestParameter.USER;

public class MovieServiceImpl implements IMovieService {
    private static Logger logger = Logger.getLogger(MovieServiceImpl.class);
    private static final int MIN_MARK = 1;
    private static final int MAX_MARK = 10;
    private static final int MIN_RATING = 0;
    private static final int DISPERSION_VALUE = 1;
    private static final int INCREMENT_VALUE = 10;
    private static final String FORMAT_DATE = "YYYY-MM-dd";
    private static final String FORMAT_TIME = "HH:mm";
    private static final int BUFFER_LENGTH = 1024;
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void addMovie(String title, String budgetString, String profitString,
                         String durationString, String restrictionAgeString, String dateReleaseString,
                         String type, String[] countriesID, String[] personsID,
                         String[] positionsID, String[] genresID, String descriptionString,
                         Part part, String webInfPath) throws ServiceException, ServiceLogicException {

        logger.debug("MovieServiceImpl.addMovie");
        Movie movie = new Movie();
        IMovieDAO iMovieDAO = daoFactory.getIMovieDAO();
        try {
            Util.isNull(title, budgetString, profitString, durationString, restrictionAgeString, dateReleaseString, type, countriesID,
                    personsID, positionsID, genresID, descriptionString, webInfPath);
            Util.isEmptyString(title, durationString, restrictionAgeString,
                    dateReleaseString, type, descriptionString, webInfPath, descriptionString);
            Util.isEmptyMassif(countriesID, personsID, positionsID, genresID);
            int budget = 0;
            int profit = 0;
            if (!budgetString.equals("")) {
                budget = Integer.parseInt(budgetString);
            }
            if (!profitString.equals("")) {
                profit = Integer.parseInt(profitString);
            }
            Util.matchCorrectString(type);
            Util.matchDate(dateReleaseString);
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
            Date dateRelease = dateFormat.parse(dateReleaseString);
            dateFormat.applyPattern(FORMAT_TIME);
            Date durationTime = dateFormat.parse(durationString);
            movie.setTitle(title);
            movie.setBudget(budget);
            movie.setProfit(profit);
            movie.setRestrictionAge(Integer.parseInt(restrictionAgeString));
            movie.setDuration(durationTime);
            movie.setRestrictionAge(Integer.parseInt(restrictionAgeString));
            movie.setReleaseDate(dateRelease);
            movie.setType(Movie.TypeMovie.valueOf(type.toUpperCase()));
            movie.setDescription(descriptionString);
            String imageName = getImageName(part);
            if (!imageName.isEmpty()) {
                movie.setImageName(imageName);
            }
            addCountiesToMovie(movie, countriesID);
            addGenresToMovie(movie, genresID);
            addPersonsToMovie(movie, personsID, positionsID);
            iMovieDAO.addMovie(movie);
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, webInfPath);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (ParseException e) {
            throw new ServiceException("недопустимый формат даты", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.addMovie - success");
    }

    @Override
    public void editMovie(String idMovie, String title, String budgetString,
                          String profitString, String durationString, String restrictionAgeString,
                          String dateReleaseString, String type, String[] countriesID,
                          String[] personsID, String[] positionsID, String[] genresID,
                          String descriptionString, Part part, String webInfPath) throws ServiceException, ServiceLogicException {
        logger.debug("MovieServiceImpl.editMovie");
        Movie movie = new Movie();
        IMovieDAO iMovieDAO = daoFactory.getIMovieDAO();
        try {
            Util.isNull(idMovie, title, budgetString, profitString, durationString, restrictionAgeString, dateReleaseString, type, countriesID,
                    personsID, positionsID, genresID, descriptionString, webInfPath);
            Util.isEmptyString(idMovie, title, durationString, restrictionAgeString,
                    dateReleaseString, type, descriptionString, webInfPath, descriptionString);
            Util.isEmptyMassif(countriesID, personsID, positionsID, genresID);
            int budget = 0;
            int profit = 0;
            if (!budgetString.equals("")) {
                budget = Integer.parseInt(budgetString);
            }
            if (!profitString.equals("")) {
                profit = Integer.parseInt(profitString);
            }
            Util.matchCorrectString(type);
            Util.matchDate(dateReleaseString);
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
            Date dateRelease = dateFormat.parse(dateReleaseString);
            dateFormat.applyPattern(FORMAT_TIME);
            Date durationTime = dateFormat.parse(durationString);
            movie.setId(Integer.parseInt(idMovie));
            movie.setTitle(title);
            movie.setBudget(budget);
            movie.setProfit(profit);
            movie.setRestrictionAge(Integer.parseInt(restrictionAgeString));
            movie.setDuration(durationTime);
            movie.setRestrictionAge(Integer.parseInt(restrictionAgeString));
            movie.setReleaseDate(dateRelease);
            movie.setType(Movie.TypeMovie.valueOf(type.toUpperCase()));
            movie.setDescription(descriptionString);
            String imageName = getImageName(part);
            if (!imageName.isEmpty()) {
                movie.setImageName(imageName);
            }
            addCountiesToMovie(movie, countriesID);
            addGenresToMovie(movie, genresID);
            addPersonsToMovie(movie, personsID, positionsID);
            iMovieDAO.editMovie(movie);
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, webInfPath);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (ParseException e) {
            throw new ServiceException("недопустимый формат даты", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.editMovie - success");
    }

    @Override
    public void addStatistic(String loginUser, String idMovieString, String comment, String markString)
            throws ServiceException, ServiceLogicException {
        logger.debug("MovieServiceImpl.addStatistic");
        DAOFactory daoFactory = DAOFactory.getInstance();
        ICommentDAO iCommentDAO = daoFactory.getICommentDAO();
        IMarkDAO iMarkDAO = daoFactory.getIMarkDAO();
        try {
            Util.isNull(loginUser, idMovieString, comment, markString);
            //connection.setAutoCommit(false)
            int idMovie = Integer.parseInt(idMovieString);
            if (!markString.isEmpty()) {
                Integer mark = Integer.valueOf(markString);
                if (mark > MAX_MARK || mark < MIN_MARK) {
                    throw new ServiceLogicException("неверный ввод данных");
                } else {
                    iMarkDAO.addMark(loginUser, idMovie, mark);
                    changeRating(loginUser, idMovie, mark);
                }
            }
            if (!comment.isEmpty()) {
                iCommentDAO.addComment(loginUser, idMovie, comment);
            }
            //connection.commit()
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            //connection.rollback()
            throw new ServiceException("ошибка ввода, ожидается число", e);
        } catch (DAOException e) {
            //connection.rollback()
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.addStatistic - success");
    }

    private void changeRating(String loginUser, int idMovie, int mark) throws ServiceException {
        logger.debug("MovieServiceImpl.changeRating");
        IUserDAO iUserDAO = daoFactory.getIUserDAO();
        try {
            User user = daoFactory.getIUserDAO().getUserByLogin(loginUser);
            Movie movie = daoFactory.getIMovieDAO().getMovie(idMovie);
            int userRating = user.getRating();
            if (user.getRating() != MIN_RATING) {
                if ((mark - DISPERSION_VALUE) <= movie.getAverageMark() && (mark + DISPERSION_VALUE) >= movie.getAverageMark()) {
                    user.setRating(userRating + INCREMENT_VALUE);
                } else {
                    if (userRating - DISPERSION_VALUE < MIN_RATING) {
                        user.setRating(MIN_RATING);
                    } else {
                        user.setRating(userRating - INCREMENT_VALUE);
                    }
                }
            } else {
                user.setRating(userRating + INCREMENT_VALUE);
            }
            iUserDAO.changeUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.changeRating - success");
    }


    @Override
    public void removeComment(String idCommentString) throws ServiceException {
        logger.debug("MovieServiceImpl.removeComment()");
        ICommentDAO iCommentDAO = null;
        try {
            Util.isNull(idCommentString);
            Util.isEmptyString(idCommentString);
            iCommentDAO = daoFactory.getICommentDAO();
            int idComment = Integer.parseInt(idCommentString);
            iCommentDAO.removeComment(idComment);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.removeComment() - success");
    }

    @Override
    public void setRemovedMovie(String idMovie) throws ServiceException {
        logger.debug("MovieServiceImpl.setRemovedMovie()");
        IMovieDAO iMovieDAO = null;
        try {
            int id = Integer.parseInt(idMovie);
            iMovieDAO = daoFactory.getIMovieDAO();
            iMovieDAO.setRemovedMovie(id);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.setRemovedMovie() - success");
    }

    @Override
    public void setUnRemovedMovie(String idMovie) throws ServiceException {
        logger.debug("MovieServiceImpl.setUnRemovedMovie()");
        IMovieDAO iMovieDAO = null;
        try {
            Util.isNull(idMovie);
            Util.isEmptyString(idMovie);
            int id = Integer.parseInt(idMovie);
            iMovieDAO = daoFactory.getIMovieDAO();
            iMovieDAO.setUnRemovedMovie(id);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.setUnRemovedMovie() - success");
    }


    @Override
    public int getNoOfPages(int noOfRecords) throws ServiceException {
        logger.debug("MovieServiceImpl.getNoOfPages()");
        int recordsOnPage = getRecordsOnPage();
        int result = (int) Math.ceil(noOfRecords * 1.0 / recordsOnPage);
        logger.debug("MovieServiceImpl.getNoOfPages() - success");
        return result;
    }

    @Override
    public Movie getMovie(String idMovie) throws ServiceException {
        logger.debug("MovieServiceImpl.getMovie()");
        Movie movie = null;
        try {
            Util.isNull(idMovie);
            Util.isEmptyString(idMovie);
            int id = Integer.parseInt(idMovie);
            movie = daoFactory.getIMovieDAO().getMovie(id);
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.getMovie()- success");
        return movie;
    }

    @Override
    public List<Comment> getCommentsByMovie(String idMovie) throws ServiceException {
        logger.debug("MovieServiceImpl.getCommentsByMovie()");
        List<Comment> comments = null;
        try {
            Util.isNull(idMovie);
            Util.isEmptyString(idMovie);
            comments = daoFactory.getICommentDAO().getAllComments(Integer.parseInt(idMovie));
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.getCommentsByMovie() - success");
        return comments;
    }

    @Override
    public List<Movie.TypeMovie> getAllMoviesTypes() throws ServiceException {
        logger.debug("MovieServiceImpl.getAllMoviesTypes()");
        List<Movie.TypeMovie> list = null;
        try {
            list = daoFactory.getIMovieDAO().getAllMoviesTypes();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.getAllMoviesTypes() - success");
        return list;
    }

    private void addCountiesToMovie(Movie movie, String[] countriesID) {
        logger.debug("MovieServiceImpl.addCountiesToMovie()");
        for (String countryString : countriesID) {
            Country country = new Country();
            country.setId(Integer.parseInt(countryString));
            movie.addCountry(country);
        }
        logger.debug("MovieServiceImpl.addCountiesToMovie() - success");
    }

    private void addGenresToMovie(Movie movie, String[] genresID) {
        logger.debug("MovieServiceImpl.addGenresToMovie()");
        for (String genreIDString : genresID) {
            Genre genre = new Genre();
            genre.setId(Integer.parseInt(genreIDString));
            movie.addGenre(genre);
        }
        logger.debug("MovieServiceImpl.addGenresToMovie() - success");
    }

    private void addPersonsToMovie(Movie movie, String[] personsID, String[] positionsID) {
        logger.debug("MovieServiceImpl.addPersonsToMovie()");
        for (int i = 0; i < personsID.length; i++) {
            Person person = new Person();
            person.setId(Integer.parseInt(personsID[i]));
            Position position = new Position();
            position.setId(Integer.parseInt(positionsID[i]));
            movie.addPerson(position, person);
        }
        logger.debug("MovieServiceImpl.addPersonsToMovie() - success");
    }

    @Override
    public int getNoOfRecords(String positionString) throws ServiceException {
        logger.debug("MovieServiceImpl.getNoOfRecords() - success");
        RequestParameter position = RequestParameter.valueOf(positionString.toUpperCase());
        int num = 0;
        try {
            Util.isNull(positionString);
            Util.isEmptyString(positionString);
            if (position == RequestParameter.ADMIN) {
                num = daoFactory.getIMovieDAO().getNoOfRecords();
            }
            if (position == RequestParameter.USER) {
                num = DAOFactory.getInstance().getIMovieDAO().getNoOfUnDeletedRecords();
            }
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.getNoOfRecords() - success");
        return num;
    }

    //выбор сколько записей по умолчанию, зависит от логики приложения
    private int getRecordsOnPage() throws ServiceException {
        logger.debug("MovieServiceImpl.getRecordsOnPage()");
        ServiceResourceManager manager = ServiceResourceManager.getInstance();
        String recordsOnPageString = manager.getValue(RequestParameter.RECORDS_ON_PAGE.getValue());
        int recordsOnPage = 0;
        try {
            recordsOnPage = Integer.parseInt(recordsOnPageString);
        } catch (NumberFormatException e) {
            recordsOnPage = 10;
        }
        logger.debug("MovieServiceImpl.getRecordsOnPage() - success");
        return recordsOnPage;
    }

    @Override
    public List<Movie> sortByDate(int page, String positionString) throws ServiceException {
        logger.debug("MovieServiceImpl.sortByDate()");
        List<Movie> movies = null;
        RequestParameter position = RequestParameter.valueOf(positionString.toUpperCase());
        try {
            Util.isNull(positionString);
            Util.isEmptyString(positionString);
            IMovieDAO iMovieDAO = daoFactory.getIMovieDAO();
            int recordOnPage = getRecordsOnPage();
            if (position == ADMIN) {
                movies = iMovieDAO.sortAllByDate((page - 1) * recordOnPage, recordOnPage);
            }
            if (position == USER) {
                movies = iMovieDAO.sortUnRemovedByDate((page - 1) * recordOnPage, recordOnPage);
            }
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.sortByDate() - success");
        return movies;
    }

    @Override
    public List<Movie> sortByRating(int page, String positionString) throws ServiceException {
        logger.debug("MovieServiceImpl.sortByRating()");
        List<Movie> movies = null;
        RequestParameter position = RequestParameter.valueOf(positionString.toUpperCase());
        try {
            Util.isNull(positionString);
            Util.isEmptyString(positionString);
            IMovieDAO iMovieDAO = daoFactory.getIMovieDAO();
            int recordOnPage = getRecordsOnPage();
            if (position == ADMIN) {
                movies = iMovieDAO.sortAllByRating((page - 1) * recordOnPage, recordOnPage);
            }
            if (position == USER) {
                movies = iMovieDAO.sortUnRemovedByRating((page - 1) * recordOnPage, recordOnPage);
            }
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.sortByRating() - success");
        return movies;
    }

    @Override
    public List<Movie> getMovies(int page, String positionString) throws ServiceException {
        logger.debug("MovieServiceImpl.getMovies()");
        List<Movie> movies = null;
        RequestParameter position = RequestParameter.valueOf(positionString.toUpperCase());
        try {
            Util.isNull(positionString);
            Util.isEmptyString(positionString);
            IMovieDAO iMovieDAO = daoFactory.getIMovieDAO();
            int recordOnPage = getRecordsOnPage();
            if (position == ADMIN) {
                movies = iMovieDAO.getMovies((page - 1) * recordOnPage, recordOnPage);
            }
            if (position == USER) {
                movies = iMovieDAO.getUnDeletedMovies((page - 1) * recordOnPage, recordOnPage);
            }
        } catch (NumberFormatException e) {
            throw new ServiceException("недопустимый формат, ожидается число", e);
        } catch (DAOException | UtilException e) {
            throw new ServiceException(e);
        }
        logger.debug("MovieServiceImpl.getMovies() - success");
        return movies;
    }

    //реализация данной функции зависит от дизайна системы
    @Override
    public int getPage(String pageString) {
        logger.debug("MovieServiceImpl.getPage()");
        int page = 1;
        try {
            Util.isNull(pageString);
            page = Integer.parseInt(pageString);
        } catch (UtilException | NumberFormatException e) {
            page = 1;
        }
        logger.debug("MovieServiceImpl.getPage() - success");
        return page;
    }

    private void uploadImage(Part filePart, String fileName, String webInfPath) throws ServiceException, ServiceLogicException {
        try {
            logger.debug("MovieServiceImpl.uploadImage()");
            File dir = new File(webInfPath + "images" + File.separator + "films");
            if (!dir.exists()) {
                Path path = Paths.get(webInfPath + "images" + File.separator + "films");
                Files.createDirectories(path);
            }
            File file = new File(dir, fileName);
            InputStream fileContent = filePart.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[BUFFER_LENGTH];
            int len = fileContent.read(buffer);
            while (len != -1) {
                fileOutputStream.write(buffer, 0, len);
                len = fileContent.read(buffer);
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    System.out.println("не возможн закрыть файл " + e);
                }
            }
        } catch (IOException e) {
            throw new ServiceLogicException("ошибка загрузки изображения ", e);
        }
        logger.debug("MovieServiceImpl.uploadImage() - success");
    }

    private String getImageName(Part filePart) {
        logger.debug("MovieServiceImpl.getImageName()");
        String name = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        logger.debug("MovieServiceImpl.getImageName() - success");
        return name;
    }
}

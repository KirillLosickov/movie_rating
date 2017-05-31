package by.training.epam.service.factory;

import by.training.epam.service.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final MovieServiceImpl iMovieService = new MovieServiceImpl();
    private final PersonServiceImpl iPersonService = new PersonServiceImpl();
    private final UserServiceImpl iUserService = new UserServiceImpl();
    private final CountryServiceImpl iCountryService = new CountryServiceImpl();
    private final GenreServiceImpl iGenreService = new GenreServiceImpl();
    private final PositionServiceImpl iPositionService = new PositionServiceImpl();

    private ServiceFactory() {

    }

    public PositionServiceImpl getPositionService() {
        return iPositionService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public MovieServiceImpl getMovieServiceImpl() {
        return iMovieService;
    }

    public PersonServiceImpl getPersonServiceImpl() {
        return iPersonService;
    }

    public GenreServiceImpl getGenreServiceImpl() {
        return iGenreService;
    }

    public UserServiceImpl getUserServiceImpl() {
        return iUserService;
    }

    public CountryServiceImpl getCountryServiceImpl() {
        return iCountryService;
    }
}

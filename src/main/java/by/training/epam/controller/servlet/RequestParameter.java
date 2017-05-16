package by.training.epam.controller.servlet;

/**
 * This enum contains user's request parameters.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public enum RequestParameter {
    INFORMATION("information"),
    TITLE("title"),
    BUDGET("budget"),
    PROFIT("profit"),
    DURATION("duration"),
    RESTRICTION_AGE("restriction_age"),
    DATE_RELEASE("date_release"),
    TYPE("type"),
    COUNTRY("country"),
    POSITION("position"),
    GENRE("genre"),
    DESCRIPTION("description"),
    COMMENT("comment"),
    MARK("mark"),
    LOGIN("login"),
    ID_USER("id_user"),
    PAGINATION("pagination"),
    SORT_FILMS_BY_DATE("sort_films_by_date"),
    RECORDS_ON_PAGE("recordsOnPage"),
    SORT_FILMS_BY_RATING("sort_films_by_rating"),
    MOVIE_LIST("movieList"),
    NO_OF_PAGES("noOfPages"),
    CURRENT_PAGE("currentPage"),
    MOVIE("movie"),
    USER("user"),
    COMMENT_LIST("comments"),
    ADMIN("admin"),
    USER_LOCAL("userLocal"),
    LOCAL("local"),
    WELCOME_LOCAL("welcomeLocal"),
    USER_LOGIN("user_login"),
    COMMAND("command"),
    SHOW_MOVIES("show_movies"),
    MOVIE_TYPES("movieTypes"),
    GENRE_LIST("genres"),
    COUNTRY_LIST("countries"),
    POSITION_LIST("positions"),
    PERSON_LIST("persons"),
    USER_LIST("userList"),
    PASSWORD("password"),
    EMAIL("email"),
    NAME("name"),
    LASTNAME("lastname"),
    PAGE("page"),
    ID_MOVIE("id_movie"),
    PERSON ("person"),
    POSTER ("poster"),
    ID_COMMENT("id_comment"),
    RATING("rating");

    /**
     * Property - value
     */
    private String value;

    /** Create new enum with the given values
     * @param value - parameter of request
     */
    RequestParameter(String value) {
        this.value = value;
    }

    /**
     * Function for get value {@link RequestParameter#value}
     * @return parameter of request
     */
    public String getValue() {
        return value;
    }
}

package by.training.epam.controller.servlet;


/**
 * This enum contains path to jsp page.
 * @author Sergei Kalashynski
 * @version 1.0
 */
public enum JspPageName {
    ERROR_PAGE("/jsp/error/error.jsp"),
    ADMIN_USER_PAGE("/jsp/admin-user.jsp"),
    WELCOME_PAGE("/jsp/welcome.jsp"),
    INDEX_PAGE("index.jsp"),
    MOVIE_PAGE("/jsp/showMovie.jsp"),
    EDIT_MOVIE_PAGE("/jsp/editMovie.jsp"),
    USERS_PAGE("/jsp/showUsers.jsp"),
    PERSONAL_USER_PAGE("/jsp/personalUser.jsp"),
    ADD_MOVIE_PAGE("/jsp/addMovie.jsp");

    /**
     * Property - path
     */
    private String path;

    /** Create new enum with the given values
     * @param path - path of jsp page
     */
    JspPageName(String path) {
        this.path = path;
    }

    /**
     * Function for get value {@link JspPageName#path}
     * @return path of jsp page
     */
    public String getPath() {
        return path;
    }
}

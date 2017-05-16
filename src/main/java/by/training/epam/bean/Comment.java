package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> userLogin </ b>, <b> idMovie </ b> and <b> value </ b>.
 * It implements interface {@see Serializable}
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Comment implements Serializable {
    /**
     * Property - id
     */
    private int id;

    /**
     * Property - user's login
     */
    private String userLogin;

    /**
     * Property movie's id
     */
    private int idMovie;

    /**
     * Property - value of comment
     */
    private String value;

    /**
     * Create new empty object
     */
    public Comment() {
    }

    /**
     * Function for get value {@link Comment#id}
     * @return identification number of comment
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set value {@link Comment#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Comment#userLogin}
     * @return user's login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Function for set value {@link Comment#userLogin}
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Function for get value {@link Comment#idMovie}
     * @return identification number of movie which has this comment
     */
    public int getIdMovie() {
        return idMovie;
    }

    /**
     * Function for set value {@link Comment#idMovie}
     */
    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    /**
     * Function for get value {@link Comment#value}
     * @return value of comment
     */
    public String getValue() {
        return value;
    }

    /**
     * Function for set value of comment {@link Comment#value}
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (idMovie != comment.idMovie) return false;
        if (userLogin != null ? !userLogin.equals(comment.userLogin) : comment.userLogin != null) return false;
        return value != null ? value.equals(comment.value) : comment.value == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + idMovie;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", idMovie=" + idMovie +
                ", value='" + value + '\'' +
                '}';
    }
}

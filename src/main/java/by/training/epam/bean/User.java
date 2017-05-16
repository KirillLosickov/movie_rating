package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> login </ b>, <b> password </ b>,
 * <b> password </ b>
 * It implements interface {@see Serializable}
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Property - id
     */
    private int id;
    /**
     * Property - login
     */
    private String login;
    /**
     * Property - password
     */
    private String password;
    /**
     * Property - name
     */
    private String name;
    /**
     * Property - second name
     */
    private String lastName;
    /**
     * Property - email
     */
    private String email;
    /**
     * Property - position
     */
    private String position;
    /**
     * Property - rating
     */
    private int rating;
    /**
     * Property - blocking
     */
    private boolean block;
    /**
     * Property - locale
     */
    private String local;

    /**
     * Create new empty object
     */
    public User() {
    }

    /** Create new object with the given values
     * @param login - login of user
     * @param password - password of user
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /** Create new object with the given values
     * @param login - login of user
     * @param password - password of user
     * @param name- name of user
     * @param lastName - second name of user
     * @param email - email of user
     * @param local - locale of user
     */
    public User(String login, String password, String name, String lastName,String email,String local) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.local= local;
    }

    /** Create new object with the given values
     * @param id - id of user
     * @param login - login of user
     * @param password - password of user
     * @param name- name of user
     * @param lastName - second name of user
     * @param email - email of user
     * @param position - position of user
     * @param rating - rating of user
     * @param block - block of user
     * @param local - locale of user
     */
    public User(int id, String login, String password, String name, String lastName, String email, String position, int rating, boolean block, String local) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.rating = rating;
        this.block = block;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    /**
     * Function for set value {@link User#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link User#login}
     * @return user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Function for set value {@link User#login}
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Function for get value {@link User#password}
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Function for set value {@link User#password}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Function for get value {@link User#email}
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Function for set value {@link User#email}
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Function for get value {@link User#name}
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Function for set value {@link User#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function for get value {@link User#lastName}
     * @return user's second name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Function for set value {@link User#lastName}
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Function for get value {@link User#position}
     * @return user's position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Function for set value {@link User#position}
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public int getRating() {
        return rating;
    }

    /**
     * Function for set value {@link User#rating}
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Function for get value {@link User#rating}
     * @return user's rating
     */
    public boolean isBlock() {
        return block;
    }

    /**
     * Function for set value {@link User#block}
     */
    public void setBlock(boolean block) {
        this.block = block;
    }

    /**
     * Function for get value {@link User#block}
     * @return user's blocking
     */
    public String getLocal() {
        return local;
    }

    /**
     * Function for set value {@link User#local}
     */
    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (rating != user.rating) return false;
        if (block != user.block) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (position != user.position) return false;
        return local != null ? local.equals(user.local) : user.local == null;

    }

    @Override
    public int hashCode() {

        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (block ? 1 : 0);
        result = 31 * result + (local != null ? local.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", position=" + position +
                ", rating=" + rating +
                ", block=" + block +
                ", local='" + local + '\'' +
                '}';
    }
}

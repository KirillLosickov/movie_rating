package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b> and <b> name </ b>.
 * It implements interface {@see Serializable}
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Genre implements Serializable{
    /**
     * Property - id
     */
    private int id;
    /**
     * Property - name
     */
    private String name;

    /**
     * Create new empty object
     */
    public Genre() {
    }

    /** Create new object with the given values
     * @param id - id of genre
     * @param name - name of genre
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Function for get value {@link Genre#id}
     * @return id of genre
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set value of comment {@link Genre#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Genre#name}
     * @return id of genre
     */
    public String getName() {
        return name;
    }

    /**
     * Function for set value of comment {@link Genre#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        return name != null ? name.equals(genre.name) : genre.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

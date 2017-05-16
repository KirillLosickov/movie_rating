package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b> and <b> name </ b>.
 * It implements interface {@see Serializable}
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Country implements Serializable{
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
    public Country() {
    }

    /** Create new object with the given values
     * @param id - id of country
     * @param name - name of country
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Function for get value {@link Country#id}
     * @return id of country
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set value of comment {@link Country#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Country#name}
     * @return name of country
     */
    public String getName() {
        return name;
    }

    /**
     * Function for set value of comment {@link Country#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (id != country.id) return false;
        return name != null ? name.equals(country.name) : country.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

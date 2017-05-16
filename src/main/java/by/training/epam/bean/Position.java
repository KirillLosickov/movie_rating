package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b> and <b> value </ b>.
 * It implements interface @see Serializable
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Position implements Serializable {
    /**
     * Property - id
     */
    private int id;
    /**
     * Property - name
     */
    private String value;

    /**
     * Create new empty object
     */
    public Position() {
    }

    /** Create new object with the given values
     * @param id - id of position
     * @param value - value of position
     */
    public Position(int id, String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Function for get value {@link Position#id}
     * @return id of position
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set id of position {@link Position#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Position#value}
     * @return value of position
     */
    public String getValue() {
        return value;
    }

    /**
     * Function for set value fo position {@link Position#value}
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != position.id) return false;
        return value != null ? value.equals(position.value) : position.value == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}

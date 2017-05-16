package by.training.epam.bean;

import java.io.Serializable;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> name </ b> and <b> second name</ b>
 * It implements interface @see Serializable
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Person implements Serializable {
    /**
     * Property - id
     */
    private int id;
    /**
     * Property - name
     */
    private String name;
    /**
     * Property - second name
     */
    private String lastname;

    /**
     * Create new empty object
     */
    public Person() {
    }

    /** Create new object with the given values
     * @param id - id of person
     * @param name - name of person
     * @param lastname - second name of person
     */
    public Person(int id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }

    /**
     * Function for get value {@link Person#id}
     * @return id of person
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set id of person {@link Person#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Person#name}
     * @return name of person
     */
    public String getName() {
        return name;
    }

    /**
     * Function for set name of person {@link Person#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function for get value {@link Person#lastname}
     * @return second name of person
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Function for set second name of person {@link Person#lastname}
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return lastname != null ? lastname.equals(person.lastname) : person.lastname == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}

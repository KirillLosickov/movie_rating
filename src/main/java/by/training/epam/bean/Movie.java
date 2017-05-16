package by.training.epam.bean;

import java.io.Serializable;
import java.util.*;

/**
 * The class store objects with properties
 * <B> id </ b>, <b> userLogin </ b>, <b> title </ b> , <b> releaseDate </ b>,
 * <b> type </ b>, <b> restrictionAge </ b>, <b> imageName </ b>, <b> duration </ b>,
 * <b> deleted </ b>, <b> budget </ b>, <b> profit </ b>, <b> description </ b>, <b> averageMark </ b>.
 * It implements interface @see Serializable
 * @autor Sergei Kalashynski
 * @version 1.0
 */
public class Movie implements Serializable {
    private static final long serialVersionUID = 3L;

    /**
     * Property - id
     */
    private int id;

    /**
     * Property - title
     */
    private String title;

    /**
     * Property - releaseDate
     */
    private Date releaseDate;

    /**
     * Property - type
     * @see Movie.TypeMovie
     */
    private TypeMovie type;

    /**
     * Property - restrictionAge
     */
    private int restrictionAge;

    /**
     * Property - imageName
     */
    private String imageName;

    /**
     * Property - duration
     */
    private Date duration;

    /**
     * Property - deleted
     */
    private boolean deleted;

    /**
     * Property - budget
     */
    private int budget;

    /**
     * Property - profit
     */
    private int profit;

    /**
     * Property - description
     */
    private String description;

    /**
     * Property - averageMark
     */
    private double averageMark;

    /**
     * Property - countries
     */
    private Set<Country> countries = new HashSet<>();

    /**
     * Property - members
     */
    private Map<Position, Set<Person>> members = new HashMap<>();

    /**
     * Property - genres
     */
    private Set<Genre> genres = new HashSet<>();

    /**
     * Create new empty object
     */
    public Movie() {
    }

    /** Create new object with the given values
     * @param id - id of movie
     * @param title - title of movie
     * @param releaseDate - release date of movie
     * @param type - type of movie
     * @param restrictionAge - restriction age for movie
     * @param imageName - name movie's image
     * @param duration - movie's duration
     * @param deleted - meaning that the movie is deleted or not
     * @param budget - budget of film
     * @param profit - profit of movie
     * @param description - description of movie
     * @param averageMark - average movie's mark
     * @param countries - countries in which the film was shot
     * @param members - members who are involved in the film
     * @param genres - genres of film
     */
    public Movie(int id, String title, Date releaseDate, TypeMovie type, int restrictionAge, String imageName, Date duration,
                 boolean deleted, int budget, int profit, String description, double averageMark, Set countries, Map<Position,
            Set<Person>> members, Set<Genre> genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.type = type;
        this.restrictionAge = restrictionAge;
        this.imageName = imageName;
        this.duration = duration;
        this.deleted = deleted;
        this.budget = budget;
        this.profit = profit;
        this.description = description;
        this.averageMark = averageMark;
        this.countries = countries;
        this.members = members;
        this.genres = genres;
    }

    /**
     * Function for get value {@link Movie#id}
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Function for set value of movie's id {@link Movie#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Function for get value {@link Movie#title}
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function for set value of comment {@link Genre#id}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function for get value {@link Movie#releaseDate}
     * @return value of date release
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Function for get value {@link Movie#title}
     * @return value of title
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Function for get value {@link Movie.TypeMovie}
     * @return value of type movie
     */
    public TypeMovie getType() {
        return type;
    }

    /**
     * Function for set value of movie's type {@link Movie.TypeMovie}
     */
    public void setType(TypeMovie type) {
        this.type = type;
    }

    /**
     * Function for get value {@link Movie#restrictionAge}
     * @return value of restriction age
     */
    public int getRestrictionAge() {
        return restrictionAge;
    }

    /**
     * Function for set value of movie's restriction age {@link Movie#restrictionAge}
     */
    public void setRestrictionAge(int restrictionAge) {
        this.restrictionAge = restrictionAge;
    }

    /**
     * Function for get value {@link Movie#imageName}
     * @return value of image's name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Function for set value of movie's image name {@link Movie#imageName}
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Function for get value {@link Movie#duration}
     * @return value of movie's duration
     */
    public Date getDuration() {
        return duration;
    }

    /**
     * Function for set value of duration of movie {@link Movie#duration}
     */
    public void setDuration(Date duration) {
        this.duration = duration;
    }

    /**
     * Function for check meaning that the movie is deleted or not {@link Movie#deleted}
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Function for check meaning that the movie is deleted or not {@link Movie#deleted}
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Function for get value {@link Movie#budget}
     * @return value of movie's budget
     */
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Function for get value {@link Movie#profit}
     * @return value of movie's profit
     */
    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    /**
     * Function for get value {@link Movie#description}
     * @return value of movie's movie's description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Function for get value {@link Movie#averageMark}
     * @return value of movie's movie's average mark
     */
    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }


    public enum TypeMovie implements Serializable {
        SERIAL, FILM;
        private static final long serialVersionUID = 4L;

    }

    /**
     * Function for get value {@link Movie#countries}
     * @return value of countries in which the film was shot
     */
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    /**
     * Function for get value {@link Movie#countries}
     * @return value of members who are involved in the film
     */
    public Map<Position, Set<Person>> getMembers() {
        return members;
    }

    public void setMembers(Map<Position, Set<Person>> members) {
        this.members = members;
    }

    public void addPerson(Position position, Person person) {
        Set<Person> persons = this.members.get(position);
        if (persons != null) {
            persons.add(person);
        } else {
            Set<Person> set= new HashSet<Person>();
            set.add(person);
            this.members.put(position, set);
        }
    }

    /**
     * Function for get value {@link Movie#countries}
     * @return value of movie's genres
     */
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Function for add genre on set {@link Movie#genres}
     */
    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    /**
     * Function for add country on set {@link Movie#countries}
     */
    public void addCountry(Country country) {
        this.countries.add(country);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (restrictionAge != movie.restrictionAge) return false;
        if (deleted != movie.deleted) return false;
        if (budget != movie.budget) return false;
        if (profit != movie.profit) return false;
        if (Double.compare(movie.averageMark, averageMark) != 0) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null) return false;
        if (type != movie.type) return false;
        if (imageName != null ? !imageName.equals(movie.imageName) : movie.imageName != null) return false;
        if (duration != null ? !duration.equals(movie.duration) : movie.duration != null) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null) return false;
        if (countries != null ? !countries.equals(movie.countries) : movie.countries != null) return false;
        if (members != null ? !members.equals(movie.members) : movie.members != null) return false;
        return genres != null ? genres.equals(movie.genres) : movie.genres == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + restrictionAge;
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + budget;
        result = 31 * result + profit;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(averageMark);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (countries != null ? countries.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", type=" + type +
                ", restrictionAge=" + restrictionAge +
                ", imageName='" + imageName + '\'' +
                ", duration=" + duration +
                ", deleted=" + deleted +
                ", budget=" + budget +
                ", profit=" + profit +
                ", description='" + description + '\'' +
                ", averageMark=" + averageMark +
                ", countries=" + countries +
                ", members=" + members +
                ", genres=" + genres +
                '}';
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

class Movie {
    private SimpleObjectProperty<MovieData> data;
    private SimpleStringProperty title;
    private SimpleStringProperty director;
    private SimpleIntegerProperty duration; // in minutes
    private SimpleDoubleProperty ticketPrice;
    private String[] showtimes;
    private SimpleStringProperty showtimeOpted;

    public Movie(SimpleObjectProperty<MovieData> data) {
        this.data = data;
        this.title = data.getValue().getTitle();
        this.director = data.getValue().getDirector();
        this.duration = data.getValue().getDuration();
        this.ticketPrice = data.getValue().getPrice();
        this.showtimes = data.getValue().getShowtimes();
        this.showtimeOpted = new SimpleStringProperty("Haven't picked!");
    }

    public SimpleObjectProperty<MovieData> getData() {
        return this.data;
    }

    public SimpleStringProperty getTitleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public SimpleStringProperty getDirector() {
        return director;
    }

    public SimpleIntegerProperty getDuration() {
        return duration;
    }

    public SimpleDoubleProperty getPriceProperty() {
        return ticketPrice;
    }

    public double getPrice() {
        return ticketPrice.getValue();
    }

    public String[] getShowTime() {
        return this.showtimes;
    }

    static final Comparator<Movie> comparatorByName = Comparator.comparing(Movie::getTitle);
    static final Comparator<Movie> comparatorByPrice = Comparator.comparing(Movie::getPrice);

    public void setShowtimeOpted(int index) {
        for (int i = 0; i < this.showtimes.length; i++) {
            if (i == index) {
                this.showtimeOpted = new SimpleStringProperty(this.showtimes[i]);
            }
        }
    }

    public void setShowtimeOpted(String string) {
        for (String s : this.showtimes) {
            if (s.equals(string)) {
                this.showtimeOpted = new SimpleStringProperty(s);
            }
        }
    }

    public SimpleStringProperty getShowTimeOptedProperty() {
        return this.showtimeOpted;
    }

    public String getDetails() {
        return "Title: " + title.get() + " - Director: " + director + " - Duration: " + duration + " minutes" + " - Showtime: " + this.showtimeOpted.getValue() + " - Price: $" + ticketPrice.intValue();
    }

    // toString method to return a string representation of a movie
    @Override
    public String toString() {
        return "Title: " + title + ", Price: " + ticketPrice;
    }
}

class HorrorMovie extends Movie {
    private SimpleStringProperty scareFactor; // Scale from 1 to 10
    private boolean supernaturalElements; // Whether the movie contains supernatural elements

    public HorrorMovie(SimpleObjectProperty<MovieData> data, String scareFactor, boolean supernaturalElements) {
        super(data);
        this.scareFactor = new SimpleStringProperty(scareFactor);
        this.supernaturalElements = supernaturalElements;
    }

    public SimpleStringProperty getScareFactorProperty() {
        return scareFactor;
    }

    public String getGenre() {
        return "Horror";
    }

    public SimpleStringProperty getsupernaturalElements() {
        if (this.supernaturalElements) {
            return new SimpleStringProperty("Yes");
        } else {
            return new SimpleStringProperty("No");
        }
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - Genre: " + getGenre() + " - Scare Factor: " + scareFactor + " - Supernatural Elements: " + this.getsupernaturalElements();
    }

    // toString method to return a string representation of a horror movie
    public String toString() {
        return super.toString() + ", Genre: " + getGenre();
    }
}

class ComedyMovie extends Movie {
    private SimpleStringProperty humorLevel; // Scale from 1 to 10
    private SimpleStringProperty jokeStyle;

    public ComedyMovie(SimpleObjectProperty<MovieData> data, String humorLevel, String jokeStyle) {
        super(data);
        this.humorLevel = new SimpleStringProperty(humorLevel);
        this.jokeStyle = new SimpleStringProperty(jokeStyle);
    }

    public SimpleStringProperty getHumorLevelProperty() {
        return humorLevel;
    }

    public SimpleStringProperty getJokeStyle() {
        return jokeStyle;
    }

    public String getGenre() {
        return "Comedy";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - Genre: " + getGenre() + " - Humor Level: " + humorLevel + " - Joke Style: " + jokeStyle;
    }

    public String toString() {
        return super.toString() + ", Genre: " + getGenre();
    }
}

class RomanticMovie extends Movie {
    private SimpleStringProperty romanceLevel; // Scale from 1 to 10
    private SimpleStringProperty leadCouple; // Names of the lead couple

    public RomanticMovie(SimpleObjectProperty<MovieData> data, String romanceLevel, String leadCouple) {
        super(data);
        this.romanceLevel = new SimpleStringProperty(romanceLevel);
        this.leadCouple = new SimpleStringProperty(leadCouple);
    }

    public SimpleStringProperty getRomanceLevel() {
        return romanceLevel;
    }

    public SimpleStringProperty getLeadCouple() {
        return leadCouple;
    }

    public String getGenre() {
        return "Romantic";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - Genre: " + getGenre() + " - Romance Level: " + romanceLevel + " - Lead Couple: " + this.leadCouple;
    }

    public String toString() {
        return super.toString() + ", Genre: " + getGenre();
    }
}

enum MovieData {
    THE_CONJURING("The Conjuring", "James Wan", 10.99, 125, new String[]{"10:00 AM", "1:00 PM", "6:00 PM"}),
    THE_HANGOVER("The Hangover", "Todd Phillips", 9.99, 90, new String[]{"11:30 AM", "2:00 PM", "7:00 PM"}),
    THE_NOTEBOOK("The Notebook", "Nick Cassavetes", 8.99, 100, new String[]{"12:00 PM", "3:00 PM", "8:00 PM"}),
    ANNABELLE("Annabelle", "John R. Leonetti", 9.50, 120, new String[]{"11:00 AM", "2:30 PM", "7:00 PM"}),
    WHITE_CHICKS("White Chicks", "Keenen Ivory Wayans", 10.99, 130, new String[]{"12:00 PM", "3:00 PM", "8:00 PM"}),
    THE_PROPOSAL("The Proposal", "Anne Fletcher", 8.99, 90, new String[]{"10:00 AM", "1:00 PM", "6:00 PM"}),
    TITANIC("Titanic", "James Cameron", 9.99, 120, new String[]{"1:00 PM", "3:00 PM", "7:00 PM"}),
    CALL_ME_BY_YOUR_NAME("Call Me By Your Name", "Luca Guadagnino", 10.99, 130, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    LIGHTS_OUT("Lights out", "David F. Sandberg", 8.99, 90, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    THE_NUN("The Nun", "Corin Hardy", 9.99, 120, new String[]{"10:00 PM", "3:00 PM", "9:00 PM"}),
    HALLOWEEN("Halloween", "David Gordon Green", 8.50, 110, new String[]{"3:00 PM", "5:00 PM", "10:00 PM"}),
    FRIDAY_THE_13TH("Friday the 13th", "Marcus Nispel", 8.99, 90, new String[]{"2:00 PM", "5:30 PM", "9:00 PM"}),
    IT("IT", "Andy Muschietti", 9.99, 120, new String[]{"1:00 PM", "3:00 PM", "7:00 PM"}),
    SMILE("Smile", "Parker Finn", 10.99, 90, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    A_QUIET_PLACE("A quiet place", "John Krasinski", 10.99, 120, new String[]{"1:00 PM", "3:30 PM", "7:00 PM"}),
    SAW("Saw", "James Wan", 8.99, 150, new String[]{"10:00 AM", "3:00 PM", "9:00 PM"}),
    GIRLS_TRIP("Girls Trip", "Malcolm D. Lee", 9.99, 120, new String[]{"12:00 PM", "3:00 PM", "8:30 PM"}),
    GROWNS_UP("Grown Ups", "Dennis Dugan", 8.99, 90, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    TED("Ted", "Seth MacFarlane", 8.99, 100, new String[]{"3:00 PM", "5:00 PM", "10:00 PM"}),
    T21_JUMP_STREET("21 Jump Street", "Phil Lord, Chris Miller", 9.99, 90, new String[]{"2:30 PM", "5:00 PM", "9:00 PM"}),
    CENTRAL_INTELLIGENCE("Central Intelligence", "Rawson Marshall Thurber", 9.99, 120, new String[]{"12:00 PM", "3:00 PM", "8:00 PM"}),
    EXTREME_JOB("Extreme Job", "Lee Byeong-heon", 10.99, 120, new String[]{"1:30 PM", "3:00 PM", "7:00 PM"}),
    JUMANJI("Jumanji", "Jake Kasdan, Joe Johnston", 9.99, 90, new String[]{"10:00 AM", "3:00 PM", "9:00 PM"}),
    THE_VOW("The Vow", "Michael Sucsy", 8.99, 100, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    ME_BEFORE_YOU("Me Before You", "Thea Sharrock", 9.99, 120, new String[]{"3:00 PM", "5:00 PM", "10:00 PM"}),
    THE_IDEA_OF_YOU("The Idea of You", "Michael Showalter", 10.99, 120, new String[]{"3:00 PM", "5:00 PM", "10:00 PM"}),
    FIVE_FEET_APART("Five Feet Apart", "Justin Baldoni", 9.99, 90, new String[]{"1:30 PM", "3:00 PM", "7:30 PM"}),
    PAST_LIVES("Past Lives", "Celine Song", 8.99, 150, new String[]{"12:00 PM", "3:00 PM", "8:30 PM"}),
    ONE_DAY("One Day", "Lone Scherfig", 9.99, 120, new String[]{"2:00 PM", "5:00 PM", "9:00 PM"}),
    BEFORE_SUNRISE("Before Sunrise", "Richard Linklater", 8.99, 90, new String[]{"2:00 PM", "5:30 PM", "9:00 PM"});

    private final String title;
    private final String director;
    private final double price;
    private final String[] time;
    private int duration; // in minutes

    MovieData(String title, String director, double price, int duration, String[] time) {
        this.title = title;
        this.director = director;
        this.price = price;
        this.time = time;
        this.duration = duration;
    }

    public SimpleStringProperty getTitle() {
        return new SimpleStringProperty(title);
    }

    public SimpleStringProperty getDirector() {
        return new SimpleStringProperty(director);
    }

    public SimpleDoubleProperty getPrice() {
        return new SimpleDoubleProperty(price);
    }

    public SimpleIntegerProperty getDuration() {
        return new SimpleIntegerProperty(duration);
    }

    public String[] getShowtimes() {
        return time;
    }

    public String toString() {
        return this.name();
    }
}

class MovieLibrary {
    static ArrayList<Movie> movieList = new ArrayList<>(Arrays.asList(new Movie[] {
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.THE_CONJURING), "9", true), 
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.THE_HANGOVER), "8", "slapstick"), 
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.THE_NOTEBOOK), "9", "Ryan Gosling and Rachel McAdams"), 
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.ANNABELLE), "10", true), 
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.WHITE_CHICKS), "9", "dark humor"), 
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.THE_PROPOSAL), "8", "witty"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.TITANIC), "9", "Kate Winslet and Leonardo DiCaprio"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.CALL_ME_BY_YOUR_NAME), "10", "Timothée Chalamet and Armie Hammer"),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.LIGHTS_OUT), "8", true),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.THE_NUN), "8", true),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.HALLOWEEN), "6", false),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.FRIDAY_THE_13TH), "7", false),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.IT), "7", true),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.SMILE), "8", true),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.A_QUIET_PLACE), "8", true),
        new HorrorMovie(new SimpleObjectProperty<>(MovieData.SAW), "9", false),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.GIRLS_TRIP), "10", "witty"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.GROWNS_UP), "8", "slapstick"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.TED), "9", "dark humor"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.T21_JUMP_STREET), "8", "slapstick"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.CENTRAL_INTELLIGENCE), "9", "witty"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.EXTREME_JOB), "10", "witty"),
        new ComedyMovie(new SimpleObjectProperty<>(MovieData.JUMANJI), "9", "slapstick"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.THE_VOW), "9", "Rachel McAdams and Channing Tatum"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.ME_BEFORE_YOU), "10", "Sam Claflin and Emilia Clarke"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.THE_IDEA_OF_YOU), "9", "Anne Hathaway and Nicholas Galitzine"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.FIVE_FEET_APART), "10", "Haley Lu Richardson and Cole Sprouse"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.PAST_LIVES), "8", "Greta Lee and Teo Yoo"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.ONE_DAY), "9", "Anne Hathaway and Jim Sturgess"),
        new RomanticMovie(new SimpleObjectProperty<>(MovieData.BEFORE_SUNRISE), "8", "Ethan Hawke and Julie Delpy")
    })); 

    public String toString() {
        return "Movie Library";
    }
}


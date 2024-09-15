import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Interface defining a method to compute the total price
interface TotalPrice {
    SimpleDoubleProperty computeTotalPrice();
}

// Interface defining a method to get the price
interface GetPrice {
    SimpleDoubleProperty getPrice();
}

// Interface extending both GetPrice and TotalPrice interfaces
interface getPriceAndToTalPrice extends GetPrice, TotalPrice {
}

// FinalBookingModel is part of the Model in the MVC pattern.
// It handles data management and business logic related to movie bookings, snacks, drinks, and combo offers.
class FinalBookingModel {

    // Model Attributes
    private List<Movie> movieList; // List of all available movies
    private ArrayList<SnackType> snackMenu; // Menu of available snacks
    private ArrayList<DrinkType> drinkMenu; // Menu of available drinks
    private ArrayList<ComboOffer> comboOffers; // List of available combo offers
    private ObservableList<Movie> moviesPickedList; // List of selected movies for booking
    private ObservableList<Snack> snackPickedList; // List of selected snacks for booking
    private ObservableList<Drink> drinkPickedList; // List of selected drinks for booking
    private ObservableList<ComboOffer> comboPickedList; // List of selected combo offers for booking
    private ArrayList<Employee> employees; // List of employees working in the system
    private Employee employee; // The employee assigned to handle the booking
    private SimpleObjectProperty<Customer> customer; // The customer making the booking
    private ArrayList<String> prizes; // List of prizes that can be won
    private double minimumPaidForPrizes; // Minimum amount required to qualify for a prize
    private SimpleDoubleProperty totalPrice; // Total price of the booking

    FinalBookingModel() {

        // Initialize movie list from MovieLibrary
        this.movieList = MovieLibrary.movieList;

        // Initialize snack and drink menus with predefined options
        this.snackMenu = new ArrayList<>(Arrays.asList(SnackType.CHIPS, SnackType.COOKIES, SnackType.FRIES, SnackType.POPCORN));
        this.drinkMenu = new ArrayList<>(Arrays.asList(DrinkType.COKE, DrinkType.FANTA, DrinkType.LEMONADE, DrinkType.MILK_TEA, DrinkType.SPRITE));

        // Initialize combo offers with predefined options
        this.comboOffers = new ArrayList<>(Arrays.asList(
            new ComboOffer("Combo 1", new Snack[] {new Snack(SnackType.POPCORN, 1, 0, false), new Snack(SnackType.CHIPS, 1, 0, false)}, new Drink[] {new Drink(DrinkType.COKE, 2, 0, false)}, 25),
            new ComboOffer("Combo 2", new Snack[] {new Snack(SnackType.FRIES, 1, 0, false)}, new Drink[] {new Drink(DrinkType.SPRITE, 2, 0, false)}, 15),
            new ComboOffer("Combo 3", new Snack[] {new Snack(SnackType.COOKIES, 2, 0, false)}, new Drink[] {new Drink(DrinkType.LEMONADE, 2, 0, false)}, 20),
            new ComboOffer("Combo 4", new Snack[] {new Snack(SnackType.POPCORN, 2, 0, false), new Snack(SnackType.CHIPS, 2, 0, false)}, new Drink[] {new Drink(DrinkType.MILK_TEA, 1, 0, false), new Drink(DrinkType.COKE, 4, 0, false)}, 50),
            new ComboOffer("Combo 5", new Snack[] {new Snack(SnackType.POPCORN, 2, 0, false), new Snack(SnackType.FRIES, 2, 0, false)}, new Drink[] {new Drink(DrinkType.LEMONADE, 1, 0, false), new Drink(DrinkType.SPRITE, 4, 0, false)}, 50)
            ));

        // Initialize observable lists for selected items
        this.moviesPickedList = FXCollections.observableArrayList();
        this.snackPickedList = FXCollections.observableArrayList();
        this.drinkPickedList = FXCollections.observableArrayList();
        this.comboPickedList = FXCollections.observableArrayList();

        // Initialize list of employees with sample data
        this.employees = new ArrayList<>(Arrays.asList(
            new Employee(new SimpleStringProperty("Vincent"), "261003", new SimpleStringProperty("viha2610@gmail.com"), new SimpleStringProperty("0902003256"), "Staff", 26),
            new Employee(new SimpleStringProperty("Mark"), "210523", new SimpleStringProperty("mark2105@gmail.com"), new SimpleStringProperty("0293888666"), "Staff", 30),
            new Employee(new SimpleStringProperty("Micheal"), "251678", new SimpleStringProperty("micheal2516@gmail.com"), new SimpleStringProperty("0465233788"), "Staff", 27)
            ));

        this.customer = null;
        this.employee = null;
        this.prizes = new ArrayList<>(Arrays.asList("Cups", "Stuffed Animal", "Toy"));
        this.minimumPaidForPrizes = 50;
        this.totalPrice = new SimpleDoubleProperty(0);
    }

    // Getters and setters for various properties
    
    public List<Movie> getMovieList() {
        return this.movieList;
    }

    public ArrayList<SnackType> getSnackMenu() {
        return this.snackMenu;
    }

    public ArrayList<DrinkType> getDrinkMenu() {
        return this.drinkMenu;
    }

    public ArrayList<ComboOffer> getComboOffers() {
        return this.comboOffers;
    }

    public ObservableList<Snack> getSnackPickedList() {
        return this.snackPickedList;
    }

    public ObservableList<Movie> getMoviePickedList() {
        return this.moviesPickedList;
    }

    public ObservableList<Drink> getDrinkPickedList() {
        return this.drinkPickedList;
    }

    public ObservableList<ComboOffer> getComboPickedList() {
        return this.comboPickedList;
    }

    public SimpleObjectProperty<Customer> getCustomer() {
        return this.customer;
    }

    public void setCustomer(SimpleObjectProperty<Customer> customer) {
        this.customer = customer;
    }

    public void setNewCustomer(SimpleObjectProperty<Customer> customer) {
        this.setCustomer(customer);
    }

    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Retrieves a random employee from the list
    public Employee getEmployee() {
        Random rand = new Random();
        int random = rand.nextInt(this.employees.size());

        this.setEmployee(this.employees.get(random));

        return this.employee;
    }

    public ArrayList<String> getPrizes() {
        return this.prizes;
    }

    public String getPrize(int index) {
        String prize = this.getPrizes().get(index);
        return prize;
    }

    // Retrieves a random prize index from the list
    public int randomPrize() {
        Random rand = new Random();
        int random = rand.nextInt(this.getPrizes().size());
        return random;
    }

    public double getMinimumPaidForPrize() {
        return this.minimumPaidForPrizes;
    }

    // Lists and sorts movies by genre and attributes methods

    public ArrayList<Movie> listHorrors() {
        ArrayList<Movie> listHorrors = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof HorrorMovie) {
                listHorrors.add(s);
            }
        }        
        
        return listHorrors;
    }

    public ArrayList<Movie> listHorrors(String scareFactor) {
        ArrayList<Movie> listHorrors = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof HorrorMovie) {
                if (((HorrorMovie) s).getScareFactorProperty().getValue().equals(scareFactor)) {
                    listHorrors.add(s);
                }
            }
        }
        
        if (!listHorrors.isEmpty()) {
            return listHorrors;
        } else {
            return null;
        }
    }

    public ArrayList<Movie> listComedys() {
        ArrayList<Movie> listComedys = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof ComedyMovie) {
                listComedys.add(s);
            }
        }        
        
        return listComedys;
    }

    public ArrayList<Movie> listComedys(String humorLevel) {
        ArrayList<Movie> listComedys = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof ComedyMovie) {
                if (((ComedyMovie) s).getHumorLevelProperty().getValue().equals(humorLevel)) {
                    listComedys.add(s);
                }
            }
        }
        
        if (!listComedys.isEmpty()) {
            return listComedys;
        } else {
            return null;
        }
    }

    public ArrayList<Movie> listRomantics() {
        ArrayList<Movie> listRomantics = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof RomanticMovie) {
                listRomantics.add(s);
            }
        }        
        
        return listRomantics;
    }

    public ArrayList<Movie> listRomantics(String romanceLevel) {
        ArrayList<Movie> listRomantics = new ArrayList<>();

        for (Movie s : this.movieList) {
            if (s instanceof RomanticMovie) {
                if (((RomanticMovie) s).getRomanceLevel().get().equals(romanceLevel)) {
                    listRomantics.add(s);
                }
            }
        }
        
        if (!listRomantics.isEmpty()) {
            return listRomantics;
        } else {
            return null;
        }
    }


    public ArrayList<Movie> sortListByName(ArrayList<Movie> movies) {
        Collections.sort(movies, Movie.comparatorByName);
        return movies;
    }

    public ArrayList<Movie> sortListByPrice(ArrayList<Movie> movies) {
        Collections.sort(movies, Movie.comparatorByPrice);
        return movies;
    }

    // Adds, removes and modifies items in the booking lists

    public void addMovie(Movie movie) {
        this.moviesPickedList.add(movie);
    }

    public int getMoviePickedIndex(Movie movieSelected) {
        int index = this.moviesPickedList.indexOf(movieSelected);
        return index;
    }

    public void removeMovie(int index) {
        this.moviesPickedList.remove(index);
    }

    public int getSnackPickedIndex(Snack snack) {
        int index = this.snackPickedList.indexOf(snack);
        return index;
    }

    public void addSnack(Snack snack) {
        this.snackPickedList.add(snack);
    }

    public void removeSnack(int index) {
        this.snackPickedList.remove(index);
    }

    public void modifySnackQuantity(int index, int quantity) {
        this.snackPickedList.get(index).setQuantity(quantity);
    }

    public int getDrinkPickedIndex(Drink drink) {
        int index = this.drinkPickedList.indexOf(drink);
        return index;
    }

    public void addDrink(Drink drink) {
        this.drinkPickedList.add(drink);
    }

    public void removeDrink(int index) {
        this.drinkPickedList.remove(index);
    }

    public void modifyDrinkQuantity(int index, int quantity) {
        this.drinkPickedList.get(index).setQuantity(quantity);
    }

    public int getComboMenuIndex(ComboOffer combo) {
        int index = this.comboOffers.indexOf(combo);
        return index;
    }

    public int getComboPickedIndex(ComboOffer combo) {
        int index = this.comboPickedList.indexOf(combo);
        return index;
    }

    public void addCombo(int index) {
        this.comboPickedList.add(this.comboOffers.get(index));
        this.comboOffers.remove(index);
    }

    public void removeCombo(int index) {
        this.comboOffers.add(this.comboPickedList.get(index));
        this.comboPickedList.remove(index);
    }

    public void sortComboOffers() {
        Collections.sort(this.comboOffers, ComboOffer.comparator);
    }

    public SimpleDoubleProperty totalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double price) {
        this.totalPrice.set(price);
    }
}

// BookingsModel is part of the Model in the MVC pattern.
// It manages booking records and customer data, including their interaction with the booking system.
class BookingsModel{

    // ArrayList to store booking records, which are instances of FinalBookingModel.
    // This list holds all booking information for the application.
    private ArrayList<FinalBookingModel> bookings;

    // ArrayList to store customer data as SimpleObjectProperty<Customer>.
    // Using SimpleObjectProperty allows for binding and observing changes in customer data.
    private ArrayList<SimpleObjectProperty<Customer>>customerList;

    BookingsModel() {
        this.bookings = new ArrayList<>();
        this.customerList = new ArrayList<>();
    }

    /**
     * Finds a customer in the customer list based on their name and password.
     * This method iterates through the customerList to find a customer whose name and password match the provided values.
     * 
     * param name: The name of the customer to be searched for.
     * param password: The password of the customer to be verified.
     * return SimpleObjectProperty<Customer> representing the found customer, or null if no matching customer is found.
     * 
     * This method encapsulates the logic for customer authentication within the Model, adhering to the MVC pattern where
     * the Model manages the application's data and business rules.
     */
    public SimpleObjectProperty<Customer> findCustomer(String name, String password) {
        // Iterate through the customerList to find a match for the provided name and password
        for (SimpleObjectProperty<Customer> c : this.getCustomerList()) {
            // Check if the customer's name and password match the provided values
            if (c.get().getName().get().equals(name) && c.get().getPassword().get().equals(password)) {
                return c; // Return the matching customer
            }
        }
        // Return null if no customer matches the provided name and password
        return null;
    }

    // Getter for the list of booking records.
    // Provides access to the list of FinalBookingModel instances representing all bookings.
    public ArrayList<FinalBookingModel> getBookings() {
        return this.bookings;
    }

    // Getter for the list of customer properties.
    // Provides access to the list of SimpleObjectProperty<Customer> instances representing all customers.
    public List<SimpleObjectProperty<Customer>> getCustomerList() {
        return this.customerList;
    }

    public void addNewCustomer(SimpleObjectProperty<Customer> newCustomer) {
        this.getCustomerList().add(newCustomer);
    }
}

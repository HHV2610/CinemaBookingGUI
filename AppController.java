import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;

/**
 * The BookingsController class acts as a mediator between the view and the model for booking operations.
 * It handles user inputs related to bookings and interacts with the BookingsModel to retrieve or update data.
 * This is part of the Controller component in the MVC pattern, responsible for processing user actions
 * and updating the model as well as the view.
 */
class BookingsController {
    private final BookingsModel model;

    public BookingsController(BookingsModel model) {
        this.model = model;
    }

    /**
     * Finds a customer in the model using their name and password.
     * 
     * param name The customer's name.
     * param password The customer's password.
     * return A SimpleObjectProperty containing the customer object if found, otherwise null.
     */
    public SimpleObjectProperty<Customer> findCustomer(String name, String password) {
        return this.model.findCustomer(name, password);
    }

    // Add new customer to BookingModel's data
    public void addNewCustomer(SimpleObjectProperty<Customer> newCustomer) {
        this.model.addNewCustomer(newCustomer);
    }
}

/**
 * The FinalBookingController class manages the final booking process, including movie selection,
 * snack and drink management, and applying combo offers. It serves as the intermediary between the view
 * and the FinalBookingModel, handling user inputs and updating the booking data accordingly.
 * This is part of the Controller component in the MVC pattern, responsible for updating the model and
 * notifying the view of changes.
 */
class FinalBookingController {
    private final FinalBookingModel model;

    FinalBookingController(FinalBookingModel model) {
        this.model = model;
    }

    /**
     * Sets the current customer in the model.
     * 
     * param customer: A SimpleObjectProperty containing the customer.
     */
    public void setCustomer(SimpleObjectProperty<Customer> customer) {
        model.setCustomer(customer);
    }

    public void setNewCustomer(String name, String id, String email, String phone, String password) {
        Customer newCustomer = new Customer(new SimpleStringProperty(name), id, new SimpleStringProperty(email), new SimpleStringProperty(phone), 0, new SimpleStringProperty(password));
        this.model.setNewCustomer(new SimpleObjectProperty<>(newCustomer));
    }

    /**
     * Retrieves a list of horror movies from the model.
     * 
     * return An ArrayList of horror movies.
     */
    public ArrayList<Movie> listHorrors() {
        return model.listHorrors();
    }

    /**
     * Retrieves a list of horror movies filtered by scare factor from the model.
     * 
     * param scareFactor: The scare factor used to filter the horror movies.
     * return an ArrayList of horror movies filtered by the specified scare factor.
     */
    public ArrayList<Movie> listHorrors(String scareFactor) {
        return model.listHorrors(scareFactor);
    }

    /**
     * Retrieves a list of comedy movies from the model.
     * 
     * return an ArrayList of comedy movies.
     */
    public ArrayList<Movie> listComedys() {
        return model.listComedys();
    }

    /**
     * Retrieves a list of comedy movies filtered by humor level from the model.
     * 
     * param humorLevel: The humor level used to filter the comedy movies.
     * return An ArrayList of comedy movies filtered by the specified humor level.
     */
    public ArrayList<Movie> listComedys(String humorLevel) {
        return model.listComedys(humorLevel);
    }

    /**
     * Retrieves a list of romantic movies from the model.
     * 
     * return An ArrayList of romantic movies.
     */
    public ArrayList<Movie> listRomantics() {
        return model.listRomantics();
    }

    /**
     * Retrieves a list of romantic movies filtered by romance level from the model.
     * 
     * param romanceLevel: The romance level used to filter the romantic movies.
     * return An ArrayList of romantic movies filtered by the specified romance level.
     */
    public ArrayList<Movie> listRomantics(String romanceLevel) {
        return model.listRomantics(romanceLevel);
    }

    /**
     * Sorts a list of movies by name using the model's sorting method.
     * 
     * param movies: The list of movies to be sorted.
     * return The sorted list of movies.
     */
    public ArrayList<Movie> sortListByName(ArrayList<Movie> movies) {
        return model.sortListByName(movies);
    }

    /**
     * Sorts a list of movies by price using the model's sorting method.
     * 
     * param movies: The list of movies to be sorted.
     * return The sorted list of movies.
     */
    public ArrayList<Movie> sortListByPrice(ArrayList<Movie> movies) {
        return model.sortListByPrice(movies);
    }

    /**
     * Adds a movie to the model's list of selected movies.
     * 
     * param movie: The movie to be added.
     * param selectedShowtime: the showtime selected
     */
    public void addMovie(Movie movie, String selectedShowtime) {
        movie.setShowtimeOpted(selectedShowtime);
        Movie newMovie = new Movie(movie.getData());
        newMovie.setShowtimeOpted(movie.getShowTimeOptedProperty().getValue());
        this.model.addMovie(newMovie);
    }

    /**
     * Removes a movie from the model's list of selected movies by its index.
     * 
     * param index: The index of the movie to be removed.
     */
    public void removeMovie(int index) {
        if (!this.model.getMoviePickedList().isEmpty()) {
            this.model.removeMovie(index); // Updates the model to remove the movie at the specified index
        }
    }

    /**
     * Adds a snack to the model's list of selected snacks.
     * 
     * param snack: The snack to be added.
     */
    public void addSnack(SnackType snackSelected, int quantity, double discountRate, boolean isDiscount) {
        Snack snack = new Snack(snackSelected, quantity, discountRate, isDiscount);
        model.addSnack(snack);
    }

    /**
     * Removes a snack from the model's list of selected snacks by its index.
     * 
     * param index: The index of the snack to be removed.
     */
    public void removeSnack(int index) {
        if(index < this.model.getSnackPickedList().size() && index >= 0) {
            this.model.removeSnack(index);
        } 
    }

    /**
     * Modifies the quantity of a snack in the model's list of selected snacks.
     * 
     * param index: The index of the snack to be modified.
     * param quantity: The new quantity for the snack.
     */
    public void modifySnackQuantity(int index, int quantity) {
        if(index < this.model.getSnackPickedList().size() && index >= 0) {
            model.modifySnackQuantity(index, quantity);
        } 
    }

    /**
     * Adds a drink to the model's list of selected drinks.
     * 
     * param drink: The drink to be added.
     */
    public void addDrink(DrinkType drinkSelected, int quantity, double discountRate, boolean isDiscount) {
        Drink drink = new Drink(drinkSelected, quantity, discountRate, isDiscount);
        model.addDrink(drink);
    }

    /**
     * Removes a drink from the model's list of selected drinks by its index.
     * 
     * param index: The index of the drink to be removed.
     */
    public void removeDrink(int index) {
        if(index < this.model.getDrinkPickedList().size() && index >= 0) {
            model.removeDrink(index); // Updates the model to remove the drink at the specified index
        } 
    }

    /**
     * Modifies the quantity of a drink in the model's list of selected drinks.
     * 
     * param index: The index of the drink to be modified.
     * param quantity: The new quantity for the drink.
     */
    public void modifyDrinkQuantity(int index, int quantity) {
        if(index < this.model.getDrinkPickedList().size() && index >= 0) {
            model.modifyDrinkQuantity(index, quantity);
        } 
    }

    /**
     * Adds a combo offer to the model's list of selected combo offers by its index.
     * 
     * param index: The index of the combo offer to be added.
     */
    public void addCombo(int index) {
        if (index < this.model.getComboOffers().size() && index >= 0) {
            model.addCombo(index);
        } 
    }

    /**
     * Removes a combo offer from the model's list of selected combo offers by its index.
     * 
     * param index: The index of the combo offer to be removed.
     */
    public void removeCombo(int index) {
        if(index < this.model.getComboPickedList().size() && index >= 0) {
            model.removeCombo(index);
            model.sortComboOffers();
        } 
    }

    /**
     * Retrieves the total price of all selected items (movies, snacks, drinks, and combos) from the model.
     * 
     * return A SimpleDoubleProperty containing the total price.
     */
    // public SimpleDoubleProperty totalPrice() {
    //     return model.totalPrice();
    // }

    /**
     * Generates a random ID as a string.
     * This method creates a Random object to generate a random integer between 0 and 9999,
     * and then converts it to a string. The random ID can be used for various purposes,
     * such as creating unique identifiers for bookings or users.
     * 
     * The reason this method is placed in the Controller is that it is part of the application logic
     * for generating unique identifiers which is triggered by user actions and workflow processes.
     * The ID generation doesn't play any part in data's logic of this application so far, just a random range of number
     * so it should not be in the Model as yet.
     * 
     * return A randomly generated ID as a string.
     */
    public String randomID () {
        Random rand = new Random();  // Create a Random object for generating a random ID
        String id = rand.nextInt(10000) + "";  // Generate a random ID as a string    
        return id;
    }

    /**
     * Determines whether a discount should be applied randomly.
     * This method uses a Random object to generate a random integer (0 or 1),
     * where 1 indicates a discount should be applied and 0 indicates no discount.
     * 
     * The reason this method is placed in the Controller is that the application may need to decide
     * whether to apply a discount based on user interactions or other business logic. The Controller
     * handles application-specific logic and user decisions, and thus manages the decision-making process
     * related to discounts.
     * 
     * The discount wasn't generated by or related to any data of the Model so it should be in the Controller
     * Another possibility, if the Model have a list of discount rate data that should be retrieved from, then this should be put in the Model
     * However, it's not that in this case
     * 
     * return True if a discount should be applied, otherwise false.
     */
    public boolean randomIsDiscount() {
        Random rand = new Random();
        int random = rand.nextInt(2);
    
        if (random == 1) {
            return true;
        }
        return false;
    }

    /**
     * Generates a random discount rate.
     * This method creates a Random object to generate a random integer between 1 and 9,
     * and then multiplies it by 10 to determine the discount rate (10, 20, ..., 90).
     * 
     * This method belongs in the Controller because the generation of discount rates can be part of
     * application workflow or user interactions (e.g., promotional offers, random discounts).
     * The discount rate doesn't require any data from the Model to be generated, which dont include data's logic management 
     * 
     * return A randomly generated discount rate (10, 20, ..., 90).
     */
    public double generateDiscountRate() {
        double discountRate = 0;
        Random randRate = new Random();
        int randomRate = (randRate.nextInt(9)) + 1; 
        discountRate = randomRate * 10;   
        return discountRate;
    }

    /**
     * Retrieves a random prize using the model.
     * The random prize is randomly retrieved from the prizes list of the Model.
     * Therefore, The Model should take control of this method
     * 
     * return A randomly selected prize from the model.
     */
    public int randomPrize() {
        return this.model.randomPrize();
    }

    /**
    * This method returns a SimpleDoubleProperty that represents the total price of all selected items.
    * It sets up listeners for changes in the observable lists of movies, snacks, drinks, and combos.
    * Whenever there's a change in any of these lists, it recalculates the total price accordingly.
    * 
    * In the MVC pattern, this method primarily handles the "Controller" responsibilities. It:
    * 1. Listens for changes in the observable lists (movies, snacks, drinks, combos) from the model.
    * 2. Recalculates the total price based on the current state of these lists.
    * 3. Updates the model with the new total price.
    */
    public SimpleDoubleProperty totalPrice() {

        // Add a listener to the moviesPickedList observable list to detect changes
        this.model.getMoviePickedList().addListener((ListChangeListener<Movie>) event -> {

            // Check if the movie list is not empty
            if(!this.model.getMoviePickedList().isEmpty()) {

                // Reset total price to 0 before recalculating
                this.model.setTotalPrice(0);

                // Calculate the total price of selected movies
                for (Movie s : this.model.getMoviePickedList()) {
                    this.model.setTotalPrice(this.model.totalPrice().get() + s.getPrice());
                } 

                // Check if the snack list is not empty and add snack prices
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack n : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(this.model.totalPrice().get() + n.computeTotalPrice().get());
                    }
                } 

                // Check if the drink list is not empty and add drink prices
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        this.model.setTotalPrice(this.model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }

                // Check if the combo list is not empty and add combo offer prices
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            } else {
                // If no movies are picked, only consider snacks, drinks, and combos
                this.model.setTotalPrice(0);

                // Recalculate the total price for snacks if not empty
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack n : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.computeTotalPrice().get());
                    }
                } 

                // Recalculate the total price for drinks if not empty
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        model.totalPrice().set(model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }

                // Recalculate the total price for combo offers if not empty
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            }
        });
        
        // Add a listener to the snackPickedList observable list to detect changes
        this.model.getSnackPickedList().addListener((ListChangeListener<Snack>) event -> {

            // Check if the snack list is not empty
            if(!this.model.getSnackPickedList().isEmpty()) {
                model.setTotalPrice(0); // Reset total price before recalculating

                // Calculate the total price for snacks
                for (Snack n : this.model.getSnackPickedList()) {
                    this.model.setTotalPrice(model.totalPrice().get() + n.computeTotalPrice().get());
                }

                // Include movies in the total if any are picked
                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                } 

                // Include drinks in the total if any are picked
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }

                // Include combo offers in the total if any are picked
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            } else {
                // If no snacks are picked, recalculate based on movies, drinks, and combos
                this.model.setTotalPrice(0);

                // Include movies in the total if any are picked
                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                } 

                // Include drinks in the total if any are picked
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }

                // Include combo offers in the total if any are picked
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            }
        });

        // Add a listener to the drinkPickedList observable list to detect changes
        this.model.getDrinkPickedList().addListener((ListChangeListener<Drink>) event -> {

            // Check if the drink list is not empty
            if(!this.model.getDrinkPickedList().isEmpty()) {
                this.model.setTotalPrice(0); // Reset total price before recalculating

                // Calculate the total price for drinks
                for (Drink d : this.model.getDrinkPickedList()) {
                    this.model.setTotalPrice(model.totalPrice().get() + d.computeTotalPrice().get());
                }

                // Include movies in the total if any are picked
                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                } 

                // Include snacks in the total if any are picked
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack s : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + s.computeTotalPrice().get());
                    }
                }

                // Include combo offers in the total if any are picked
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            } else {
                // If no drinks are picked, recalculate based on movies, snacks, and combos
                this.model.setTotalPrice(0);

                // Include movies in the total if any are picked
                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                } 

                // Include snacks in the total if any are picked
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack s : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + s.computeTotalPrice().get());
                    }
                }

                // Include combo offers in the total if any are picked
                if(!this.model.getComboPickedList().isEmpty()) {
                    for (ComboOffer c : this.model.getComboPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                    }
                }
            }
        });

        // Add a listener to the comboPickedList observable list to detect changes
        this.model.getComboPickedList().addListener((ListChangeListener<ComboOffer>) event -> {

            // Check if the combo list is not empty
            if(!this.model.getComboPickedList().isEmpty()) {
                model.setTotalPrice(0); // Reset total price before recalculating

                // Calculate the total price for combo offers
                for (ComboOffer c : this.model.getComboPickedList()) {
                    this.model.setTotalPrice(model.totalPrice().get() + c.getPrice().get());
                }

                // Include movies in the total if any are picked
                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                }
                
                // Include snacks in the total if any are picked
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack s : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + s.computeTotalPrice().get());
                    }
                }

                // Include drinks in the total if any are picked
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }
            } else {
                // If no combos are picked, recalculate based on movies, snacks, and drinks
                model.setTotalPrice(0);

                if(!this.model.getMoviePickedList().isEmpty()) {
                    for (Movie n : this.model.getMoviePickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + n.getPrice());
                    }
                } 
                if(!this.model.getSnackPickedList().isEmpty()) {
                    for (Snack s : this.model.getSnackPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + s.computeTotalPrice().get());
                    }
                }
                if(!this.model.getDrinkPickedList().isEmpty()) {
                    for (Drink d : this.model.getDrinkPickedList()) {
                        this.model.setTotalPrice(model.totalPrice().get() + d.computeTotalPrice().get());
                    }
                }
            }
        });

        return this.model.totalPrice();
    }
}

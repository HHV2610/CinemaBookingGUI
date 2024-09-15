import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AppView {
    private VBox view;
    private Button bookingBtn;
    private Button exitBtn;
    private BookingsController bookingsController;
    private FinalBookingController finalBookingController;
    private BookingsModel bookingsModel;
    private FinalBookingModel finalBookingModel;
    private Stage primaryStage;
    private SimpleBooleanProperty endBooking;
    private String currentCustomerName;
    private String currentCustomerPassword;
    private ObservableList<Movie> movieData;
    private ObservableList<Snack> snackData;
    private ObservableList<Drink> drinkData;
    private ObservableList<ComboOffer> comboData;

    public AppView(Stage primaryStage) {

        this.bookingsModel = new BookingsModel();
        this.bookingsController = new BookingsController(bookingsModel);
        this.primaryStage = primaryStage;
        this.endBooking = new SimpleBooleanProperty(false);

        createAndConfigurePane();
        createBookingConfirmationPrimaryPage();
    }

    public Parent asParent() {
        return view;
    }

    private void createAndConfigurePane() {
        view = new VBox(15);// Create a VBox layout with 15px spacing between elements
        view.setAlignment(Pos.CENTER);  // Center the alignment of elements within the VBox
    }

    // Method to create and layout the initial controls on the main stage
    private void createBookingConfirmationPrimaryPage() {
        this.bookingBtn = new Button("Booking");  // Create a "Booking" button
        this.bookingBtn.setOnAction(event -> {
            bookingSession(); // Manage the booking session when the button is clicked
            createLogInMenu();  // Open the login menu
            this.primaryStage.close();  // Close the primary stage
        });

        this.exitBtn = new Button("Exit"); // Create an "Exit" button
        this.exitBtn.setOnAction(event -> javafx.application.Platform.exit());  // Exit the application when clicked
    
        HBox buttonRow = new HBox(5, bookingBtn, exitBtn);  // Create an HBox to hold the buttons with 5px spacing
        buttonRow.setAlignment(Pos.CENTER);  // Center the buttons within the HBox
    
        view.getChildren().addAll(new Label("Do you want to start booking?"), buttonRow);  // Add the label and button row to the main pane
    }

    // Method to create and display the login menu
    private void createLogInMenu() {
        this.finalBookingModel = new FinalBookingModel(); // Create a new FinalBookingModel
        this.finalBookingController = new FinalBookingController(finalBookingModel);  // Create a controller for the new booking model
        this.bookingsModel.getBookings().add(finalBookingModel);  // Add the new booking to the bookings list

        Stage stage = new Stage(); // Create a new stage for the login menu
        stage.setTitle("Log In Menu");  // Set the title of the login menu
        stage.initModality(Modality.APPLICATION_MODAL);  // Make the stage modal, blocking interaction with other windows

        Button logInBtn = new Button("Log In"); // Create a "Log In" button
        logInBtn.setAlignment(Pos.CENTER);  // Center align the button

        logInBtn.setOnAction(event -> {
            createLogInForm(); // Open the login form when the button is clicked
            stage.close();  // Close the login menu stage
        });

        Button signInBtn = new Button("Sign In"); // Create a "Sign In" button
        signInBtn.setAlignment(Pos.CENTER);  // Center align the button

        signInBtn.setOnAction(event -> {
            createSignInForm();  // Open the sign-in form when the button is clicked
            stage.close();  // Close the login menu stage
        });

        Button exitBtn = new Button("Exit"); // Create an "Exit" button
        exitBtn.setAlignment(Pos.CENTER);  // Center align the button

        exitBtn.setOnAction(event -> javafx.application.Platform.exit());  // Exit the application when clicked

        Label employeeLabel = new Label(); // Create a label to display employee details
        employeeLabel.setText("Staff in charge:  " + this.finalBookingModel.getEmployee().getName().get() + " - ID: " + this.finalBookingModel.getEmployee().getId() + " - Phone: " + this.finalBookingModel.getEmployee().getPhone().get());
        employeeLabel.setTextFill(Color.GREY);  // Set the text color to grey    

        HBox row = new HBox(5, logInBtn, signInBtn, exitBtn); // Create an HBox for the buttons with 5px spacing
        row.setAlignment(Pos.CENTER);  // Center align the buttons

        VBox root = new VBox(5, new Label(), row, new Label(), employeeLabel); // Create a VBox for the entire layout with 5px spacing
        root.setAlignment(Pos.CENTER);  // Center align the VBox contents

        Scene scene = new Scene(root, 400, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create and display the login form
    private void createLogInForm() {
        Stage stage = new Stage(); // Create a new stage for the login form
        stage.setTitle("Log In");  // Set the title of the login form
        stage.initModality(Modality.APPLICATION_MODAL);  // Make the stage modal, blocking interaction with other windows

        Button logInBtn = new Button("Log In"); // Create a "Log In" button
        TextField nameText = new TextField();  // Create a text field for entering the name
        nameText.setPromptText("Enter your name");  // Set the placeholder text for the name field
        HBox nameTextRow = new HBox(5, new Label("Name:"), nameText);  // Create an HBox for the name input with 5px spacing
        nameTextRow.setAlignment(Pos.CENTER);  // Center align the HBox contents

        TextField passwordText = new TextField(); // Create a text field for entering the password
        HBox passwordTextRow = new HBox(5, new Label("Password:"), passwordText);  // Create an HBox for the password input with 5px spacing
        passwordTextRow.setAlignment(Pos.CENTER);  // Center align the HBox contents

        Label label = new Label();  // Create a label for displaying login status messages

        Button continueBtn = new Button("Continue");  // Create a "Continue" button
        continueBtn.setOnAction(event -> {
            createBooking(); // Proceed to booking creation when the button is clicked
            stage.close();  // Close the login form stage
        });

        Button exitBtn = new Button("Exit"); // Create an "Exit" button
        exitBtn.setAlignment(Pos.CENTER);  // Center align the button

        exitBtn.setOnAction(event -> {
            createLogInMenu(); // Return to the login menu when the button is clicked
            stage.close();  // Close the login form stage
        });

        HBox logInRow = new HBox(5, logInBtn, exitBtn);
        logInRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, nameTextRow, passwordTextRow, label, logInRow);
        root.setAlignment(Pos.CENTER);
                
        logInBtn.setOnAction(event -> {
            String name = nameText.getText().trim();
            String password = passwordText.getText().trim();
            this.currentCustomerName = name;
            this.currentCustomerPassword = password;
            if (!name.isEmpty()) {  // Check if the name is not empty
                if (this.bookingsController.findCustomer(name, password) != null && this.bookingsController.findCustomer(name, password).getValue().getPassword().get().equals(password)) {
                    this.finalBookingController.setCustomer(this.bookingsController.findCustomer(name, password));
                    label.setText("Welcome back! " + name); // Display a welcome message
                    label.setTextFill(Color.GREEN);  // Set the text color to green for success
                } else {
                    label.setText("Invalid Account"); // Display an error message for invalid account
                    label.setTextFill(Color.RED);  // Set the text color to red for error    
                }
                
            }    
        });

        // Add a listener to update the UI when the login status message changes
        label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().equals("Invalid Account") && !root.getChildren().contains(continueBtn)) {
                root.getChildren().remove(logInRow); // Remove the login row if the account is valid
                root.getChildren().add(continueBtn);  // Add the "Continue" button for proceeding
            }
        });      

        Scene logInScene = new Scene(root, 400, 150);

        stage.setScene(logInScene);
        stage.show();        
    }

    // Method to create and display the sign-in form
    private void createSignInForm() {
        Stage stage = new Stage(); // Create a new stage for the sign-in form
        stage.setTitle("Sign In");  // Set the title of the sign-in form
        stage.initModality(Modality.APPLICATION_MODAL);  // Make the stage modal, blocking interaction with other windows

        Button logInBtn = new Button("Sign in"); // Create a "Sign in" button
        TextField nameText = new TextField();  // Create a text field for entering the name
        nameText.setPromptText("Enter your name");  // Set the placeholder text for the name field
        HBox nameTextRow = new HBox(5, new Label("Name:"), nameText);  // Create an HBox for the name input with 5px spacing
        nameTextRow.setAlignment(Pos.CENTER);  // Center align the HBox contents

        TextField passwordText = new TextField(); // Create a text field for entering the password
        HBox passwordTextRow = new HBox(5, new Label("Password:"), passwordText);  // Create an HBox for the password input with 5px spacing
        passwordTextRow.setAlignment(Pos.CENTER);  // Center align the HBox contents    

        Label label = new Label();

        Button continueBtn = new Button("Continue");
        continueBtn.setOnAction(event -> {
            createBooking(); // Proceed to booking creation when the button is clicked
            stage.close();  // Close the sign-in form stage
        });

        Button exitBtn = new Button("Exit"); // Create an "Exit" button
        exitBtn.setAlignment(Pos.CENTER);  // Center align the button

        exitBtn.setOnAction(event -> {
            createLogInMenu(); // Return to the login menu when the button is clicked
            stage.close();  // Close the sign-in form stage
        });

        HBox logInRow = new HBox(5, logInBtn, exitBtn); // Create an HBox for the sign-in and exit buttons with 5px spacing
        logInRow.setAlignment(Pos.CENTER);  // Center align the buttons

        VBox root = new VBox(5, nameTextRow, passwordTextRow, label, logInRow); // Create a VBox for the entire sign-in form layout with 5px spacing
        root.setAlignment(Pos.CENTER);  // Center align the VBox contents
                
        logInBtn.setOnAction(event -> {
            String name = nameText.getText().trim(); // Get and trim the entered name
            String password = passwordText.getText().trim();  // Get and trim the entered password
            this.currentCustomerName = name;  // Store the entered name
            this.currentCustomerPassword = password;  // Store the entered password
            if (!name.isEmpty()) {  // Check if the name is not empty
                if (this.bookingsController.findCustomer(name, password) != null && this.bookingsController.findCustomer(name, password).getValue().getPassword().get().equals(password)) {
                    label.setText("This account is already exist!"); // Display an error message if the account already exists
                    label.setTextFill(Color.RED); // Set the text color to red for error
                } else {
                    createSubSignInForm(name, password); // Proceed to the next step of sign-in if the account doesn't exist
                    stage.close(); // Close the sign-in form stage
                }
                
            }    
        });

        // Add a listener to update the UI when the sign-in status message changes
        label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().equals("This account is already exist!") && !root.getChildren().contains(continueBtn)) {
                root.getChildren().remove(logInRow); // Remove the sign-in row if the account is valid
                root.getChildren().add(continueBtn);  // Add the "Continue" button for proceeding
            }
        });      

        Scene signInScene = new Scene(root, 400, 150);

        stage.setScene(signInScene);
        stage.show();        
    }

    // Method to create and display the secondary sign-in form for additional details
    private void createSubSignInForm(String name, String password) {
        Stage stage = new Stage();
        stage.setTitle("Sign In");
        stage.initModality(Modality.APPLICATION_MODAL);

        String id = this.finalBookingController.randomID();  // Generate a random ID as a string    

        TextField emailText = new TextField();
        emailText.setPromptText("Enter your email address");
        HBox emailTextRow = new HBox(5, new Label("Email:"), emailText);
        emailTextRow.setAlignment(Pos.CENTER);

        TextField phoneText = new TextField();
        phoneText.setPromptText("Enter your phone number");
        configTextFieldForInts(phoneText);
        HBox phoneTextRow = new HBox(5, new Label("Phone Number:"), phoneText);
        phoneTextRow.setAlignment(Pos.CENTER);

        Label label = new Label();

        Button continueBtn = new Button("Continue");
        continueBtn.setOnAction(event -> {
            createBooking();
            stage.close();
        });
        
        Button signInBtn = new Button("Sign In");
        signInBtn.setOnAction(event -> {
            String email = emailText.getText().trim(); // Get and trim the entered email
            String phone = phoneText.getText().trim();  // Get and trim the entered phone number
            if (!email.isEmpty() && !phone.isEmpty()) {  // Check if both email and phone are not empty
                this.finalBookingController.setNewCustomer(name, id, email, phone, password);
                this.bookingsController.addNewCustomer(this.finalBookingModel.getCustomer());
                label.setText("Welcome to our service! " + name);  // Display a welcome message
                label.setTextFill(Color.GREEN); // Set the text color to green for success       
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createLogInMenu(); // Return to the login menu when the button is clicked
            stage.close();
        });

        HBox signInRow = new HBox(5, signInBtn, exitBtn);
        signInRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, emailTextRow, phoneTextRow, label, signInRow);

        label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty() && !root.getChildren().contains(continueBtn)) {
                root.getChildren().remove(signInRow);
                root.getChildren().add(continueBtn);
            }
        });

        root.setAlignment(Pos.CENTER);

        Scene signInScene = new Scene(root, 400, 150);
        stage.setScene(signInScene);
        stage.show();
    }

    // This method creates and manages the booking window, showing selected movies, snacks, drinks, and combo offers.
    private void createBooking() {

        // Create a new stage for the booking list and set its modality (focus).
        Stage primaryBookingStage = new Stage();
        primaryBookingStage.initModality(Modality.APPLICATION_MODAL);
        primaryBookingStage.setTitle("Booking List");

        VBox vBox = new VBox(10);
    
        // Set up the movie table (View component).
        // This TableView displays the movies selected by the user.
        TableView<Movie> movieTable = new TableView<>();

        // Create and configure table columns for movie details: title, price, showtime, and duration.
        TableColumn<Movie, String> titleCol = new TableColumn<>("Movie Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        titleCol.setMinWidth(150);
        TableColumn<Movie, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        TableColumn<Movie, String> showtimeCol = new TableColumn<>("Showtime");
        showtimeCol.setCellValueFactory(cellData -> cellData.getValue().getShowTimeOptedProperty());
        TableColumn<Movie, Integer> durationCol = new TableColumn<>("Duration (minutes)");
        durationCol.setCellValueFactory(cellData -> cellData.getValue().getDuration().asObject());

        // Create and configure table columns for movie details: title, price, showtime, and duration.
        movieTable.getColumns().addAll(titleCol, priceCol, durationCol, showtimeCol);

        // Set up the snack table (View component).
        // This TableView displays the snacks selected by the user.
        TableView<Snack> snackTable = new TableView<>();

        // Create and configure table columns for snack details: name, price, quantity, discount rate, and total price.
        TableColumn<Snack, String> nameSnackCol = new TableColumn<>("Snack");
        nameSnackCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        nameSnackCol.setMinWidth(100);
        TableColumn<Snack, Double> priceSnackCol = new TableColumn<>("Price ($)");
        priceSnackCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Snack, Integer> quantitySnackCol = new TableColumn<>("Quantity");
        quantitySnackCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Snack, Double> discountSnackCol = new TableColumn<>("Discount Rate (%)");
        discountSnackCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Snack, Double> totalPriceSnackCol = new TableColumn<>("Total Price ($)");
        totalPriceSnackCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        // Add all columns to the snackTable.
        snackTable.getColumns().addAll(nameSnackCol, priceSnackCol, quantitySnackCol, discountSnackCol, totalPriceSnackCol);

        // Set up the drink table (View component).
        // This TableView displays the drinks selected by the user.
        TableView<Drink> drinkTable = new TableView<>();

        // Create and configure table columns for drink details: name, price, quantity, discount rate, and total price.
        TableColumn<Drink, String> nameDrinkCol = new TableColumn<>("Drink");
        nameDrinkCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        nameDrinkCol.setMinWidth(100);
        TableColumn<Drink, Double> priceDrinkCol = new TableColumn<>("Price ($)");
        priceDrinkCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Drink, Integer> quantityDrinkCol = new TableColumn<>("Quantity");
        quantityDrinkCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Drink, Double> discountDrinkCol = new TableColumn<>("Discount Rate (%)");
        discountDrinkCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Drink, Double> totalPriceDrinkCol = new TableColumn<>("Total Price ($)");
        totalPriceDrinkCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        // Add all columns to the drinkTable.
        drinkTable.getColumns().addAll(nameDrinkCol, priceDrinkCol, quantityDrinkCol, discountDrinkCol, totalPriceDrinkCol);

        // Set up the combo offer list (View component).
        // This ListView displays the combo offers selected by the user.
        ListView<ComboOffer> comboList = new ListView<>();

        // Retrieve selected items data (Model interaction).
        // These ObservableLists contain the selected items (movies, snacks, drinks, combos) from the Model.
        this.movieData = this.finalBookingModel.getMoviePickedList();
        this.snackData = this.finalBookingModel.getSnackPickedList();
        this.drinkData = this.finalBookingModel.getDrinkPickedList();
        this.comboData = this.finalBookingModel.getComboPickedList();
        
        // Bind the retrieved data to the corresponding View components (tables and list).
        // This ensures that the View automatically updates when the Model changes.
        movieTable.setItems(movieData);
        snackTable.setItems(snackData);
        drinkTable.setItems(drinkData);
        comboList.setItems(comboData);

        // Create a label to display when no items are selected
        Label label = new Label("No item");
        label.setAlignment(Pos.CENTER);

        // Bind the total price label to display the computed total price from the Controller.
        // This keeps the total price displayed in sync with the items selected.
        Label totalPriceLabel = new Label();
        totalPriceLabel.textProperty().bind(this.finalBookingController.totalPrice().asString("Your total price is: %.2f$"));
        totalPriceLabel.setAlignment(Pos.CENTER);

        // Create buttons for different booking options (View components).
        Button movieBtn = new Button("Movie Ticket");
        movieBtn.setMinWidth(130);

        // Attach an event handler to the movie button to open the movie selection menu.
        movieBtn.setOnAction(event -> {
            createMovieMenu(primaryBookingStage);
        });

        Button foodBtn = new Button("Food");
        foodBtn.setMinWidth(130);

        foodBtn.setOnAction(event -> {
            createFoodMenu(primaryBookingStage);
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            primaryBookingStage.close();
        });

        Button backToLoginBtn = new Button("Back to Login Menu");
        backToLoginBtn.setAlignment(Pos.CENTER);

        backToLoginBtn.setOnAction(event -> {
            createLogInMenu();
            primaryBookingStage.close();
        });

        Button processPayment = new Button("Proceed to Payment");
        processPayment.setOnAction(event -> {
            if (!(movieData.isEmpty() && snackData.isEmpty() && drinkData.isEmpty() && comboData.isEmpty())) {
                confirmPayment(primaryBookingStage);
            }
        });
    
        HBox primaryBtnRow = new HBox(5, movieBtn, foodBtn, processPayment);
        HBox secondaryBtnRow = new HBox(5, exitBtn, backToLoginBtn);
        primaryBtnRow.setAlignment(Pos.CENTER);
        secondaryBtnRow.setAlignment(Pos.CENTER);
        
        vBox.getChildren().addAll(label,primaryBtnRow, secondaryBtnRow);
        vBox.setAlignment(Pos.CENTER);
    
        // Add listeners to update the VBox based on selected items in the Model.
        // This ensures that the View updates in real-time as the user interacts with the Model.
        movieData.addListener((ListChangeListener<Movie>) change -> {
            if (!movieData.isEmpty()) { // If movie data is not empty, display the movie table and other data
                vBox.getChildren().setAll(movieTable);
                if (!snackData.isEmpty()) {
                    vBox.getChildren().add(snackTable);
                }
                if (!drinkData.isEmpty()) {
                    vBox.getChildren().add(drinkTable);
                }
                if (!comboData.isEmpty()) {
                    vBox.getChildren().add(comboList);
                }
                HBox btnRow = new HBox(5, movieBtn, foodBtn, processPayment, exitBtn, backToLoginBtn);
                btnRow.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(totalPriceLabel, btnRow);
            } else if (movieData.isEmpty() && snackData.isEmpty() && drinkData.isEmpty() && comboData.isEmpty()) {
                // If all data lists are empty, display a default message and buttons
                vBox.getChildren().setAll();
                HBox firstBtnRow = new HBox(5, movieBtn, foodBtn, processPayment);
                HBox secondBtnRow = new HBox(5, exitBtn, backToLoginBtn);
                firstBtnRow.setAlignment(Pos.CENTER);
                secondBtnRow.setAlignment(Pos.CENTER);
        
                vBox.getChildren().addAll(label,firstBtnRow, secondBtnRow);
                vBox.setAlignment(Pos.CENTER);
            } else if (movieData.isEmpty()) {
                // If only movie data is empty, remove the movie table
                vBox.getChildren().remove(movieTable);
            }
        });

        // Similar listeners for snack, drink, and combo data. They ensure the UI reflects changes dynamically
        snackData.addListener((ListChangeListener<Snack>) change -> {
            if (!snackData.isEmpty()) {
                vBox.getChildren().setAll();
                if (!movieData.isEmpty()) {
                    vBox.getChildren().add(movieTable);
                }
                vBox.getChildren().add(snackTable);
                if (!drinkData.isEmpty()) {
                    vBox.getChildren().add(drinkTable);
                }
                if (!comboData.isEmpty()) {
                    vBox.getChildren().add(comboList);
                }
                HBox btnRow = new HBox(5, movieBtn, foodBtn, processPayment, exitBtn, backToLoginBtn);
                btnRow.setAlignment(Pos.CENTER);    
                vBox.getChildren().addAll(totalPriceLabel, btnRow);
            } else if (movieData.isEmpty() && snackData.isEmpty() && drinkData.isEmpty() && comboData.isEmpty()) {
                vBox.getChildren().setAll();
                HBox firstBtnRow = new HBox(5, movieBtn, foodBtn, processPayment);
                HBox secondBtnRow = new HBox(5, exitBtn, backToLoginBtn);
                firstBtnRow.setAlignment(Pos.CENTER);
                secondBtnRow.setAlignment(Pos.CENTER);
        
                vBox.getChildren().addAll(label,firstBtnRow, secondBtnRow);
                vBox.setAlignment(Pos.CENTER);
            } else if (snackData.isEmpty()) {
                vBox.getChildren().remove(snackTable);
            }
        });

        // Listeners for drink and combo data, structured similarly to handle UI updates accordingly
        drinkData.addListener((ListChangeListener<Drink>) change -> {
            if (!drinkData.isEmpty()) {
                vBox.getChildren().setAll();
                if (!movieData.isEmpty()) {
                    vBox.getChildren().add(movieTable);
                }
                if (!snackData.isEmpty()) {
                    vBox.getChildren().add(snackTable);
                }
                vBox.getChildren().add(drinkTable);
                if (!comboData.isEmpty()) {
                    vBox.getChildren().add(comboList);
                }
                HBox btnRow = new HBox(5, movieBtn, foodBtn, processPayment, exitBtn, backToLoginBtn);
                btnRow.setAlignment(Pos.CENTER);    
                vBox.getChildren().addAll(totalPriceLabel, btnRow);
            } else if (movieData.isEmpty() && snackData.isEmpty() && drinkData.isEmpty() && comboData.isEmpty()) {
                vBox.getChildren().setAll();
                HBox firstBtnRow = new HBox(5, movieBtn, foodBtn, processPayment);
                HBox secondBtnRow = new HBox(5, exitBtn, backToLoginBtn);
                firstBtnRow.setAlignment(Pos.CENTER);
                secondBtnRow.setAlignment(Pos.CENTER);
        
                vBox.getChildren().addAll(label,firstBtnRow, secondBtnRow);
                vBox.setAlignment(Pos.CENTER);
            } else if (drinkData.isEmpty()) {
                vBox.getChildren().remove(drinkTable);
            }
        });

        // Listener for combo data updates UI based on combo data availability
        comboData.addListener((ListChangeListener<ComboOffer>) change -> {
            if (!comboData.isEmpty()) {
                vBox.getChildren().setAll();
                if (!movieData.isEmpty()) {
                    vBox.getChildren().add(movieTable);
                }
                if (!snackData.isEmpty()) {
                    vBox.getChildren().add(snackTable);
                }
                if (!drinkData.isEmpty()) {
                    vBox.getChildren().add(drinkTable);
                }
                vBox.getChildren().add(comboList);
                HBox btnRow = new HBox(5, movieBtn, foodBtn, processPayment, exitBtn, backToLoginBtn);
                btnRow.setAlignment(Pos.CENTER);    
                vBox.getChildren().addAll(totalPriceLabel, btnRow);
            } else if (movieData.isEmpty() && snackData.isEmpty() && drinkData.isEmpty() && comboData.isEmpty()) {
                vBox.getChildren().setAll();
                HBox firstBtnRow = new HBox(5, movieBtn, foodBtn, processPayment);
                HBox secondBtnRow = new HBox(5, exitBtn, backToLoginBtn);
                firstBtnRow.setAlignment(Pos.CENTER);
                secondBtnRow.setAlignment(Pos.CENTER);
        
                vBox.getChildren().addAll(label,firstBtnRow, secondBtnRow);
                vBox.setAlignment(Pos.CENTER);
            } else if (comboData.isEmpty()) {
                vBox.getChildren().remove(comboList);
            }
        });
    
        Scene scene = new Scene(vBox, 600, 500);
        primaryBookingStage.setScene(scene);
        primaryBookingStage.show();
    }

    // Method to create and configure the main pane
    private void bookingSession() {
        this.endBooking.addListener((observableValue, oldValue, newValue) -> {
            if (newValue == true) {  // Check if the booking session has ended
                this.finalBookingModel = new FinalBookingModel();  // Create a new FinalBookingModel
                this.finalBookingController = new FinalBookingController(finalBookingModel);  // Create a controller for the new booking model
                this.bookingsModel.getBookings().add(finalBookingModel);  // Add the new booking to the bookings list
                this.finalBookingController.setCustomer(this.bookingsController.findCustomer(currentCustomerName, currentCustomerPassword));  // Set the customer for the booking
                this.endBooking.set(false);  // Reset the endBooking flag
                createBooking();  // Start a new booking session
            } 
        });
    }
    
    // Create the movie menu stage
    private void createMovieMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Movie Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button addMovieBtn = new Button("Add Movie");
        addMovieBtn.setMinWidth(130);

        addMovieBtn.setOnAction(event -> {
            createMovieTypeMenu(primaryBookingStage);
            stage.close();
        });

        Button removeMovieBtn = new Button("Remove Movie");
        removeMovieBtn.setMinWidth(130);

        removeMovieBtn.setOnAction(event -> {
            removeMovieMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, addMovieBtn, removeMovieBtn);
        firstRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, backToMainMenuBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create the movie ticket booking menu
    private void createMovieTypeMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Movie Type");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button horrorBtn = new Button("Horror");
        horrorBtn.setMinWidth(130);

        horrorBtn.setOnAction(event -> {
            createMovieListTypeMenu(this.finalBookingController.listHorrors(), "Horror", primaryBookingStage);
            stage.close();
        });

        Button comedyBtn = new Button("Comedy");
        comedyBtn.setMinWidth(130);

        comedyBtn.setOnAction(event -> {
            createMovieListTypeMenu(this.finalBookingController.listComedys(), "Comedy", primaryBookingStage);
            stage.close();
        });

        Button romanticBtn = new Button("Romantic");
        romanticBtn.setMinWidth(130);

        romanticBtn.setOnAction(event -> {
            createMovieListTypeMenu(this.finalBookingController.listRomantics(), "Romantic", primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createMovieMenu(primaryBookingStage);
            stage.close();
        });

        HBox firstRow = new HBox(5, horrorBtn, comedyBtn, romanticBtn);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(5, exitBtn, backToMainMenuBtn);
        secondRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, secondRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    private void createMovieListTypeMenu(ArrayList<Movie> listMovies, String name, Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Movie List Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Button to sort the movie list by name
        Button sortByNameBtn = new Button("Sort list by Name");
        sortByNameBtn.setMinWidth(130);

        sortByNameBtn.setOnAction(event -> {
            createMovieList(listMovies, listMovies, name, primaryBookingStage, "Name");
            stage.close();
        });

        // Button to sort the movie list by price
        Button sortByPriceBtn = new Button("Sort list by Price");
        sortByPriceBtn.setMinWidth(130);

        sortByPriceBtn.setOnAction(event -> {
            // Opens a menu to display movies sorted by price
            createMovieList(listMovies, listMovies, name, primaryBookingStage, "Price");
            stage.close();
        });

        // Button to filter movies by specific rate
        Button sortByRateBtn = new Button("List of specific rate");
        sortByRateBtn.setMinWidth(130);

        sortByRateBtn.setOnAction(event -> {
            createEnterRate(listMovies, name, primaryBookingStage);
            stage.close();
        });

        Button normalListBtn = new Button("Normal list");
        normalListBtn.setMinWidth(130);

        normalListBtn.setOnAction(event -> {
            createMovieList(listMovies, listMovies, name, primaryBookingStage, "Normal");
            stage.close();
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createMovieTypeMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, sortByNameBtn, sortByPriceBtn);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(5, sortByRateBtn, normalListBtn);
        secondRow.setAlignment(Pos.CENTER);

        HBox thirdRow = new HBox(5, exitBtn, backToMainMenuBtn);
        thirdRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, secondRow, thirdRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create a menu for entering a specific rating to filter movies
    private void createEnterRate(ArrayList<Movie> listMovies, String name, Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Enter Rate");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField rateField = new TextField("Enter the rate");
        HBox textRow = new HBox(5, new Label("Enter the rate: "), rateField);
        textRow.setAlignment(Pos.CENTER);

        Label label = new Label();

        // Button to search movies by the entered rate
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            String text = rateField.getText().trim();
            if (name.equals("Horror")) {
                ArrayList<Movie> movies = this.finalBookingController.listHorrors(text);
                if (movies != null) {
                    createMovieList(movies, listMovies, name, primaryBookingStage, "Rate");
                    stage.close();
                } else {
                    label.setText("There is no movie with that scale!");
                    label.setTextFill(Color.RED); // Set the text color to red for error
                }
            } else if (name.equals("Comedy")) {
                ArrayList<Movie> movies = this.finalBookingController.listComedys(text);
                if (movies != null) {
                    createMovieList(movies, listMovies, name, primaryBookingStage, "Rate");
                    stage.close();
                } else {
                    label.setText("There is no movie with that scale!");
                    label.setTextFill(Color.RED); // Set the text color to red for error
                }
            } else {
                ArrayList<Movie> movies = this.finalBookingController.listRomantics(text);
                if (movies != null) {
                    createMovieList(movies, listMovies, name, primaryBookingStage, "Rate");
                    stage.close();
                } else {
                    label.setText("There is no movie with that scale!");
                    label.setTextFill(Color.RED); // Set the text color to red for error
                }
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createMovieListTypeMenu(listMovies, name, primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
           stage.close();
        });

        HBox btnRow = new HBox(5, searchButton, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, textRow, label, btnRow);

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Method to create a menu that displays movies filtered by different conditions
    private void createMovieList(ArrayList<Movie> listMovies, ArrayList<Movie> primaryListMovies, String name, Stage primaryBookingStage, String listType) {
        Stage stage = new Stage();
        stage.setTitle("List Movie (" + listType + ")");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        TableView<Movie> tableMovie = new TableView<>();
        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        titleCol.setMinWidth(20);
        TableColumn<Movie, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        TableColumn<Movie, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(cellData -> cellData.getValue().getDirector());
        TableColumn<Movie, Integer> durationCol = new TableColumn<>("Duration (minutes)");
        durationCol.setCellValueFactory(cellData -> cellData.getValue().getDuration().asObject());
        TableColumn<Movie, String> rateCol = new TableColumn<>("Rating");
        
        // Set different cell value factories based on the movie genre
        if (name.equals("Horror")) {
            rateCol.setCellValueFactory(cellData -> ((HorrorMovie) cellData.getValue()).getScareFactorProperty());
        } else if (name.equals("Comedy")) {
            rateCol.setCellValueFactory(cellData -> ((ComedyMovie) cellData.getValue()).getHumorLevelProperty());
        } else {
            rateCol.setCellValueFactory(cellData -> ((RomanticMovie) cellData.getValue()).getRomanceLevel());
        }

        ObservableList<Movie> data = FXCollections.observableArrayList();

        ArrayList<Movie> movies = null;

        // Determine which movies to display based on the filter type
        if (listType.equals("Name")) {
            movies = this.finalBookingController.sortListByName(listMovies);
        } else if (listType.equals("Price")) {
            movies = this.finalBookingController.sortListByPrice(listMovies);
        } else if (listType.equals("Rate") || listType.equals("Normal")) {
            movies = listMovies;
        } 

        for (Movie movie : movies) {
            data.add(movie);
        }

        tableMovie.getColumns().setAll(titleCol, priceCol, directorCol, durationCol, rateCol);
        tableMovie.setItems(data);

        // Button to proceed to the showtime selection menu
        Button addMovie = new Button("Add movie");
        addMovie.setOnAction(event -> {
            if (tableMovie.getSelectionModel().getSelectedItem() != null) {
                createShowtimeMenu(tableMovie, listMovies, primaryListMovies, name, primaryBookingStage, listType);
                stage.close();    
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            if (listType.equals("Rate")) {
                createEnterRate(primaryListMovies, name, primaryBookingStage);
                stage.close();    
            } else {
                createMovieListTypeMenu(listMovies, name, primaryBookingStage);
                stage.close();    
            }
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, addMovie, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5, tableMovie, btnRow);
        Scene scene = new Scene(vBox, 600, 250);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create a menu for selecting a showtime for a selected movie
    private void createShowtimeMenu(TableView<Movie> tableMovie, ArrayList<Movie> listMovies, ArrayList<Movie> primaryListMovies, String name, Stage primaryBookingStage, String listType) {
        Stage stage = new Stage();
        stage.setTitle("Showtimes");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        ListView<String> listShowTime = new ListView<>();

        // Get the selected movie and its showtimes
        ObservableList<String> dataShowTime = FXCollections.observableArrayList();
        Movie movieSelected =  tableMovie.getSelectionModel().getSelectedItem();

        // Populate the showtimes list for the selected movie
        ArrayList<String> showTimes = new ArrayList<>(Arrays.asList(movieSelected.getShowTime()));
        for (String showTime : showTimes) {
            dataShowTime.addAll(showTime);
        }   
        
        listShowTime.setItems(dataShowTime);

        // Button to select a showtime and add the movie to the final booking list
        Button selectShowTime = new Button("Select showtime");
        selectShowTime.setOnAction(e -> {
            // Ensure a showtime is selected from the list before proceeding
            if (listShowTime.getSelectionModel().getSelectedItem() != null) {
                // Controller - Interact with the Model by adding the selected movie and showtime to the final booking list
                this.finalBookingController.addMovie(movieSelected, listShowTime.getSelectionModel().getSelectedItem()); // Add the movie to the booking list in the controller
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createMovieList(listMovies, primaryListMovies, name, primaryBookingStage, listType);    
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, selectShowTime, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5, listShowTime, btnRow);
        Scene scene = new Scene(vBox, 400, 250);

        stage.setScene(scene);
        stage.show();    
    }

    // Method to create a menu for removing a movie from the final booking list
    private void removeMovieMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Remove Movie");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        // TableView to display the list of selected movies
        TableView<Movie> listMoviePicked = new TableView<>();
        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        titleCol.setMinWidth(20);
        TableColumn<Movie, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        TableColumn<Movie, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(cellData -> cellData.getValue().getDirector());
        TableColumn<Movie, String> showtimeCol = new TableColumn<>("Showtime");
        showtimeCol.setCellValueFactory(cellData -> cellData.getValue().getShowTimeOptedProperty());
        TableColumn<Movie, Integer> durationCol = new TableColumn<>("Duration (minutes)");
        durationCol.setCellValueFactory(cellData -> cellData.getValue().getDuration().asObject());

        listMoviePicked.getColumns().setAll(titleCol, priceCol, directorCol, showtimeCol, durationCol);

        Label noItemLabel = new Label("No Item");

        ObservableList<Movie> movies = this.finalBookingModel.getMoviePickedList();

        // Display the appropriate content based on whether there are selected movies
        if (movies.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listMoviePicked.setItems(movies);
            dynamicContent.getChildren().setAll(listMoviePicked); // Show ListView if there are items
        }    

        // Button to remove the selected movie from the list
        Button removeBtn = new Button("Remove Movie");
        removeBtn.setOnAction(event -> {
            // Get the selected movie from the TableView
            Movie movieSelected = listMoviePicked.getSelectionModel().getSelectedItem();
            if (movieSelected != null) {
                // Controller - Interact with the Model to remove the selected movie
                int index = this.finalBookingModel.getMoviePickedIndex(movieSelected);
                this.finalBookingController.removeMovie(index); // Remove the movie using the controller
                movies.remove(movieSelected); // Remove the movie from the observable list
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createMovieMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, removeBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.show();

        // Add a listener to update the layout dynamically when the movie list changes
        movies.addListener((ListChangeListener<Movie>) change -> {
            if (movies.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listMoviePicked.setItems(movies);
                dynamicContent.getChildren().setAll(listMoviePicked); // Show ListView if there are items
            }
        });
    }
 
    // Method to create the main food menu with options for snacks, drinks, and combos
    private void createFoodMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Food Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button snackBtn = new Button("Snack");
        snackBtn.setMinWidth(130);

        snackBtn.setOnAction(event -> {
            createSnackMenu(primaryBookingStage);
            stage.close();
        });

        Button drinkBtn = new Button("Drink");
        drinkBtn.setMinWidth(130);

        drinkBtn.setOnAction(event -> {
            createDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button comboBtn = new Button("Combo");
        comboBtn.setMinWidth(130);

        comboBtn.setOnAction(event -> {
            createComboMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, snackBtn, drinkBtn, comboBtn);
        firstRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, backToMainMenuBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    private void createSnackMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Snack Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button snackBtn = new Button("Add Snack");
        snackBtn.setMinWidth(130);

        snackBtn.setOnAction(event -> {
            createAddSnackMenu(primaryBookingStage);
            stage.close();
        });

        Button removeSnackBtn = new Button("Remove Snack");
        removeSnackBtn.setMinWidth(130);

        removeSnackBtn.setOnAction(event -> {
            createRemoveSnackMenu(primaryBookingStage);
            stage.close();
        });

        Button modifyQuantityBtn = new Button("Modify Snack Quantity");
        modifyQuantityBtn.setMinWidth(130);

        modifyQuantityBtn.setOnAction(event -> {
            createModifyQuantitySnackMenu(primaryBookingStage);
            stage.close();
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createFoodMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, snackBtn, removeSnackBtn, modifyQuantityBtn);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(5, exitBtn, backToMainMenuBtn);
        secondRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, secondRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    private void createAddSnackMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Add Snack");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        ListView<SnackType> listSnack = new ListView<>();

        ObservableList<SnackType> data = FXCollections.observableArrayList();
        ArrayList<SnackType> snacks = this.finalBookingModel.getSnackMenu();
        for (SnackType snack : snacks) {
            data.add(snack);
        }

        listSnack.setItems(data);

        // Quantity property to bind the snack quantity
        SimpleIntegerProperty quantityProperty = new SimpleIntegerProperty(0);

        Button increaseBtn = new Button("Increase");
        Button decreaseBtn = new Button("Decrease");

        Label quantityLabel = new Label();

        // Label to display the current quantity
        quantityLabel.textProperty().bind(quantityProperty.asString("Quantity: %d"));

        // Increase and decrease button event handlers
        increaseBtn.setOnAction(event -> {
            quantityProperty.set(quantityProperty.get() + 1);
        });

        decreaseBtn.setOnAction(event -> {
            if (quantityProperty.intValue() > 0) {
                quantityProperty.set(quantityProperty.get() - 1);
            } 
        });

        HBox quantityRow = new HBox(5, decreaseBtn, quantityLabel, increaseBtn);
        quantityRow.setAlignment(Pos.CENTER);

        // Button to add the selected snack
        Button addSnack = new Button("Add Snack");
        addSnack.setOnAction(event -> {
            SnackType snackSelected = listSnack.getSelectionModel().getSelectedItem();
            int quantity = quantityProperty.intValue();

            if (quantity > 0 && snackSelected != null) {

                int countDuplicate = 0;
                for (Snack s : this.finalBookingModel.getSnackPickedList()) {
                    if (snackSelected.getName().equals(s.getName().get())) {
                        countDuplicate++;
    
                        int accumulateQuantity = s.getQuantity().get() + quantity;

                        // Call the modifySnackQuantity method of the Controller which triggers the Model to modify the quantity of the item
                        this.finalBookingController.modifySnackQuantity(this.finalBookingModel.getSnackPickedIndex(s), accumulateQuantity);

                        // Update snackData list if needed
                        this.snackData.set(this.finalBookingModel.getSnackPickedIndex(s), s);
                        quantityProperty.set(0);

                        break;
                    }
                }

                if (countDuplicate == 0) {
                    boolean isDiscount = this.finalBookingController.randomIsDiscount();
                    double discountRate = 0;
                    if (isDiscount) {
                        discountRate = this.finalBookingController.generateDiscountRate();
                    }

                    // Add the snack to the final booking by trigger the Controller
                    this.finalBookingController.addSnack(snackSelected, quantity, discountRate, isDiscount);
                    quantityProperty.set(0);
                }    
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createSnackMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, addSnack, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, listSnack, quantityRow, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    private void createRemoveSnackMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Remove Snack");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);
    
        // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        // TableView to display picked snacks
        TableView<Snack> listSnackPicked = new TableView<>();
        TableColumn<Snack, String> nameCol = new TableColumn<>("Snack");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        nameCol.setMinWidth(100);
        TableColumn<Snack, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Snack, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Snack, Double> discountCol = new TableColumn<>("Discount Rate (%)");
        discountCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Snack, Double> totalPriceCol = new TableColumn<>("Total Price ($)");
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        listSnackPicked.getColumns().addAll(nameCol, priceCol, quantityCol, discountCol, totalPriceCol);

        Label noItemLabel = new Label("No Item");
    
        ObservableList<Snack> snacks = this.finalBookingModel.getSnackPickedList();
    
        if (snacks.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listSnackPicked.setItems(snacks);
            dynamicContent.getChildren().setAll(listSnackPicked); // Show ListView if there are items
        }
    
        Button removeBtn = new Button("Remove Snack");
        removeBtn.setOnAction(event -> {
            Snack snackSelected = listSnackPicked.getSelectionModel().getSelectedItem();
            if (snackSelected != null) {
                // Get selected snack index
                int index = this.finalBookingModel.getSnackPickedIndex(snackSelected);
                // Call the removeSnack method of the Controller which triggers the Model to remove the selected item by index
                this.finalBookingController.removeSnack(index);
            }
        });
    
        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);
        exitBtn.setOnAction(event -> {
            createSnackMenu(primaryBookingStage);
            stage.close();
        });
    
        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);
        backToMainMenuBtn.setOnAction(event -> stage.close());
    
        HBox btnRow = new HBox(5, removeBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);
    
        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);
    
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
    
        // Add listener to update the layout dynamically
        snacks.addListener((ListChangeListener<Snack>) change -> {
            if (snacks.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listSnackPicked.setItems(snacks);
                dynamicContent.getChildren().setAll(listSnackPicked); // Show ListView if there are items
            }
        });
    }

    private void createModifyQuantitySnackMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Modify Snack Quantity");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        TableView<Snack> listSnackPicked = new TableView<>();
        TableColumn<Snack, String> nameCol = new TableColumn<>("Snack");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        TableColumn<Snack, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Snack, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Snack, Double> discountCol = new TableColumn<>("Discount Rate (%)");
        discountCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Snack, Double> totalPriceCol = new TableColumn<>("Total Price ($)");
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        listSnackPicked.getColumns().addAll(nameCol, priceCol, quantityCol, discountCol, totalPriceCol);

        Label noItemLabel = new Label("No Item");

        ObservableList<Snack> snacks = this.finalBookingModel.getSnackPickedList();

        Label quantityLabel = new Label();

        // Quantity property to bind the snack quantity
        SimpleIntegerProperty newQuantity = new SimpleIntegerProperty();

        listSnackPicked.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Set newQuantity to reflect to the quantity of the selected item
                newQuantity.set(newValue.quantityProperty.get());
                quantityLabel.textProperty().bind(newQuantity.asString("Quantity: %d"));
            } else {
                quantityLabel.textProperty().unbind();
                quantityLabel.setText("Haven't selected");
            }
        });

        // Increase and decrease button event handlers
        Button increaseBtn = new Button("Increase");
        Button decreaseBtn = new Button("Decrease");

        increaseBtn.setOnAction(event -> {
            Snack snackSelected = listSnackPicked.getSelectionModel().getSelectedItem();
            if (snackSelected != null) {
                newQuantity.set(newQuantity.get() + 1);
            }
        });

        decreaseBtn.setOnAction(event -> {
            Snack snackSelected = listSnackPicked.getSelectionModel().getSelectedItem();
            if (snackSelected != null && newQuantity.get() > 1) {
                newQuantity.set(newQuantity.get() - 1);
            }
        });

        HBox quantityRow = new HBox(5, decreaseBtn, quantityLabel, increaseBtn);
        quantityRow.setAlignment(Pos.CENTER);

        // Button to modify the quantity of the selected snack
        Button modifyBtn = new Button("Modify Quantity");
        modifyBtn.setOnAction(event -> {
            Snack snackSelected = listSnackPicked.getSelectionModel().getSelectedItem();
            if (snackSelected != null) {
                // Get the index of the selected item from the Model
                int index = this.finalBookingModel.getSnackPickedIndex(snackSelected);

                // Call the modifySnackQuantity method of the Controller which triggers the Model to modify the quantity of the item
                this.finalBookingController.modifySnackQuantity(index, newQuantity.get());

                // Update snackData list if needed
                snacks.set(index, snackSelected);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createSnackMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        if (snacks.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listSnackPicked.setItems(snacks);
            dynamicContent.getChildren().setAll(listSnackPicked, quantityRow); // Show ListView if there are items
        }    

        HBox btnRow = new HBox(5, modifyBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.show();

        // Add listener to update the layout dynamically
        snacks.addListener((ListChangeListener<Snack>) change -> {
            if (snacks.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listSnackPicked.setItems(snacks);
                dynamicContent.getChildren().setAll(listSnackPicked, quantityRow); // Show ListView if there are items
            }
        });
    } 

    // Function to create the Drink Menu stage
    private void createDrinkMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Drink Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button drinkBtn = new Button("Add Drink");
        drinkBtn.setMinWidth(130);

        drinkBtn.setOnAction(event -> {
            createAddDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button removeDrinkBtn = new Button("Remove Drink");
        removeDrinkBtn.setMinWidth(130);

        removeDrinkBtn.setOnAction(event -> {
            createRemoveDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button modifyQuantityBtn = new Button("Modify Drink Quantity");
        modifyQuantityBtn.setMinWidth(130);

        modifyQuantityBtn.setOnAction(event -> {
            createModifyQuantityDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createFoodMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, drinkBtn, removeDrinkBtn, modifyQuantityBtn);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(5, exitBtn, backToMainMenuBtn);
        secondRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, secondRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Function to create the Add Drink Menu stage
    private void createAddDrinkMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Add Drink");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // ListView to display available drinks
        ListView<DrinkType> listDrink = new ListView<>();

        // Populate the ListView with drinks
        ObservableList<DrinkType> data = FXCollections.observableArrayList();
        ArrayList<DrinkType> drinks = this.finalBookingModel.getDrinkMenu();
        for (DrinkType drink : drinks) {
            data.add(drink);
        }

        listDrink.setItems(data);
    
        // Property to manage the drink quantity
        SimpleIntegerProperty quantityProperty = new SimpleIntegerProperty(0);

        Button increaseBtn = new Button("Increase");
        Button decreaseBtn = new Button("Decrease");

        Label quantityLabel = new Label();

        quantityLabel.textProperty().bind(quantityProperty.asString("Quantity: %d"));

        increaseBtn.setOnAction(event -> {
            quantityProperty.set(quantityProperty.get() + 1);
        });

        decreaseBtn.setOnAction(event -> {
            if (quantityProperty.intValue() > 0) {
                quantityProperty.set(quantityProperty.get() - 1);
            } 
        });

        HBox quantityRow = new HBox(5, decreaseBtn, quantityLabel, increaseBtn);
        quantityRow.setAlignment(Pos.CENTER);

        // Button to add the selected drink with the specified quantity
        Button addDrink = new Button("Add Drink");
        addDrink.setOnAction(event -> {
            DrinkType drinkSelected = listDrink.getSelectionModel().getSelectedItem();
            int quantity = quantityProperty.intValue();

            if (quantity > 0 && drinkSelected != null) {
                // Check for duplicate drinks in the picked list
                int countDuplicate = 0;
                for (Drink d : this.finalBookingModel.getDrinkPickedList()) {
                    if (drinkSelected.getName().equals(d.getName().get())) {
                        countDuplicate++;
    
                        int accumulateQuantity = d.getQuantity().get() + quantity;
    
                        // Call the modifyDrinkQuantity method of the Controller which triggers the Model to modify the quantity of the item
                        this.finalBookingController.modifyDrinkQuantity(this.finalBookingModel.getDrinkPickedIndex(d), accumulateQuantity);

                        // Update drinkData list if needed
                        this.drinkData.set(this.finalBookingModel.getDrinkPickedIndex(d), d);
                        quantityProperty.set(0);

                        break;
                    }
                }

                // If no duplicate is found, add the drink to the list
                if (countDuplicate == 0) {
                    boolean isDiscount = this.finalBookingController.randomIsDiscount();
                    double discountRate = 0;
                    if (isDiscount) {
                        discountRate = this.finalBookingController.generateDiscountRate();
                    }
    
                    this.finalBookingController.addDrink(drinkSelected, quantity, discountRate, isDiscount);
                    quantityProperty.set(0);
                }    
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, addDrink, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, listDrink, quantityRow, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    // Function to create the Remove Drink Menu stage
    private void createRemoveDrinkMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Remove Drink");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        TableView<Drink> listDrinkPicked = new TableView<>();
        TableColumn<Drink, String> nameCol = new TableColumn<>("Drink");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        nameCol.setMinWidth(100);
        TableColumn<Drink, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Drink, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Drink, Double> discountCol = new TableColumn<>("Discount Rate (%)");
        discountCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Drink, Double> totalPriceCol = new TableColumn<>("Total Price ($)");
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        listDrinkPicked.getColumns().addAll(nameCol, priceCol, quantityCol, discountCol, totalPriceCol);

        Label noItemLabel = new Label("No Item");

        ObservableList<Drink> drinks = this.finalBookingModel.getDrinkPickedList();

        // Show "No Item" label if the list is empty, otherwise show the TableView
        if (drinks.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listDrinkPicked.setItems(drinks);
            dynamicContent.getChildren().setAll(listDrinkPicked); // Show ListView if there are items
        }    

        Button removeBtn = new Button("Remove Drink");
        removeBtn.setOnAction(event -> {
            Drink drinkSelected = listDrinkPicked.getSelectionModel().getSelectedItem();
            if (drinkSelected != null) {
                int index = this.finalBookingModel.getDrinkPickedIndex(drinkSelected);

                this.finalBookingController.removeDrink(index);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, removeBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.show();

        // Add listener to update the layout dynamically
        drinks.addListener((ListChangeListener<Drink>) change -> {
            if (drinks.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listDrinkPicked.setItems(drinks);
                dynamicContent.getChildren().setAll(listDrinkPicked); // Show ListView if there are items
            }
        });
    }

    // Method to create a window for modifying drink quantities
    private void createModifyQuantityDrinkMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Modify Drink Quantity");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

            // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        // Create TableView for displaying drinks with their details
        TableView<Drink> listDrinkPicked = new TableView<>();
        TableColumn<Drink, String> nameCol = new TableColumn<>("Drink");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        TableColumn<Drink, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        TableColumn<Drink, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        TableColumn<Drink, Double> discountCol = new TableColumn<>("Discount Rate (%)");
        discountCol.setCellValueFactory(cellData -> cellData.getValue().getDiscountRate().asObject());
        TableColumn<Drink, Double> totalPriceCol = new TableColumn<>("Total Price ($)");
        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().computeTotalPrice().asObject());

        listDrinkPicked.getColumns().addAll(nameCol, priceCol, quantityCol, discountCol, totalPriceCol);
        
        Label noItemLabel = new Label("No Item");

        // Get the list of drinks picked from the model
        ObservableList<Drink> drinks = this.finalBookingModel.getDrinkPickedList();

        Label quantityLabel = new Label();

        SimpleIntegerProperty newQuantity = new SimpleIntegerProperty();

        // Listener to update quantity label when a drink is selected
        listDrinkPicked.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newQuantity.set(newValue.quantityProperty.get());
                quantityLabel.textProperty().bind(newQuantity.asString("Quantity: %d"));
            } else {
                quantityLabel.textProperty().unbind();
                quantityLabel.setText("Haven't selected");
            }
        });

        Button increaseBtn = new Button("Increase");
        Button decreaseBtn = new Button("Decrease");

        increaseBtn.setOnAction(event -> {
            Drink drinkSelected = listDrinkPicked.getSelectionModel().getSelectedItem();
            if (drinkSelected != null) {
                newQuantity.set(newQuantity.get() + 1);
            }
        });

        decreaseBtn.setOnAction(event -> {
            Drink drinkSelected = listDrinkPicked.getSelectionModel().getSelectedItem();
            if (drinkSelected != null && newQuantity.get() > 1) {
                newQuantity.set(newQuantity.get() - 1);
            }
        });

        HBox quantityRow = new HBox(5, decreaseBtn, quantityLabel, increaseBtn);
        quantityRow.setAlignment(Pos.CENTER);

        // Button to modify the quantity of the selected drink
        Button modifyBtn = new Button("Modify Quantity");
        modifyBtn.setOnAction(event -> {
            Drink drinkSelected = listDrinkPicked.getSelectionModel().getSelectedItem();
            if (drinkSelected != null) {
                int index = this.finalBookingModel.getDrinkPickedIndex(drinkSelected);

                this.finalBookingController.modifyDrinkQuantity(index, newQuantity.get());

                drinks.set(index, drinkSelected);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createDrinkMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        // Update dynamic content based on whether drinks are available
        if (drinks.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listDrinkPicked.setItems(drinks);
            dynamicContent.getChildren().setAll(listDrinkPicked, quantityRow); // Show ListView if there are items
        }    

        HBox btnRow = new HBox(5, modifyBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.show();

        // Add listener to update the layout dynamically when the drink list changes
        drinks.addListener((ListChangeListener<Drink>) change -> {
            if (drinks.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listDrinkPicked.setItems(drinks);
                dynamicContent.getChildren().setAll(listDrinkPicked, quantityRow); // Show ListView if there are items
            }
        });
    }

    // Method to create a window for the combo menu
    private void createComboMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Combo Menu");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button addComboBtn = new Button("Add Combo");
        addComboBtn.setMinWidth(130);

        addComboBtn.setOnAction(event -> {
            createAddComboMenu(primaryBookingStage);
            stage.close();
        });

        Button removeComboBtn = new Button("Remove Combo");
        removeComboBtn.setMinWidth(130);

        removeComboBtn.setOnAction(event -> {
            createRemoveComboMenu(primaryBookingStage);
            stage.close();
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createFoodMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox firstRow = new HBox(5, addComboBtn, removeComboBtn);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(5, exitBtn, backToMainMenuBtn);
        secondRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, firstRow, secondRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create a window for adding a new combo
    private void createAddComboMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Add Combo");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        ListView<ComboOffer> listCombo = new ListView<>();

        ObservableList<ComboOffer> data = FXCollections.observableArrayList();
        ArrayList<ComboOffer> combos = this.finalBookingModel.getComboOffers();
        for (ComboOffer combo : combos) {
            data.add(combo);
        }

        listCombo.setItems(data);

        Button addCombo = new Button("Add Combo");
        addCombo.setOnAction(event -> {
            ComboOffer comboSelected = listCombo.getSelectionModel().getSelectedItem();

            if (comboSelected != null) {
                this.finalBookingController.addCombo(this.finalBookingModel.getComboMenuIndex(comboSelected));
                data.remove(comboSelected);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createComboMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, addCombo, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, listCombo, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create a window for removing an existing combo
    private void createRemoveComboMenu(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Remove Combo");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

            // Create a VBox to hold the dynamic content
        VBox dynamicContent = new VBox(5);
        dynamicContent.setAlignment(Pos.CENTER);

        ListView<ComboOffer> listComboPicked = new ListView<>();
        Label noItemLabel = new Label("No Item");

        ObservableList<ComboOffer> combos = this.finalBookingModel.getComboPickedList();

        // Update dynamic content based on whether combos are available
        if (combos.isEmpty()) {
            dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
        } else {
            listComboPicked.setItems(combos);
            dynamicContent.getChildren().setAll(listComboPicked); // Show ListView if there are items
        }    

        // Button to remove the selected combo
        Button removeBtn = new Button("Remove Combo");
        removeBtn.setOnAction(event -> {
            ComboOffer comboSelected = listComboPicked.getSelectionModel().getSelectedItem();
            if (comboSelected != null) {
                int index = this.finalBookingModel.getComboPickedIndex(comboSelected);

                this.finalBookingController.removeCombo(index);
                combos.remove(comboSelected);    
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.CENTER);

        exitBtn.setOnAction(event -> {
            createComboMenu(primaryBookingStage);
            stage.close();
        });

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setAlignment(Pos.CENTER);

        backToMainMenuBtn.setOnAction(event -> {
            stage.close();
        });

        HBox btnRow = new HBox(5, removeBtn, exitBtn, backToMainMenuBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, dynamicContent, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
        stage.show();

        // Add listener to update the layout dynamically when the combo list changes
        combos.addListener((ListChangeListener<ComboOffer>) change -> {
            if (combos.isEmpty()) {
                dynamicContent.getChildren().setAll(noItemLabel); // Show "No Item" label if the list is empty
            } else {
                listComboPicked.setItems(combos);
                dynamicContent.getChildren().setAll(listComboPicked); // Show ListView if there are items
            }
        });
    }

    // Method to create a confirmation window for payment
    private void confirmPayment(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Payment Confirmation");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText("Process Payment?");

        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(event -> {
            processPayment(primaryBookingStage);
            stage.close();
        });

        Button noBtn = new Button("No");
        noBtn.setOnAction(event -> {
            stage.close();;
        });

        HBox btnRow = new HBox(5, noBtn, yesBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, label, btnRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 100);

        stage.setScene(scene);
        stage.show();
    }

    // Method to create a window for processing payment
    private void processPayment(Stage primaryBookingStage) {
        Stage stage = new Stage();
        stage.setTitle("Payment Completed");
        stage.initOwner(primaryBookingStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Set loyalty points for the customer based on the total price
        this.finalBookingModel.getCustomer().get().setLoyaltyPoints((int)this.finalBookingModel.totalPrice().get());

        Label totalPriceLabel = new Label();
        totalPriceLabel.textProperty().bind(this.finalBookingController.totalPrice().asString("Your total price is: %.2f$"));

        Label paymentResult = new Label();
        paymentResult.setText("Successful Payment!");

        Label loyalPointsLabel = new Label();
        loyalPointsLabel.setText("Your current loyal point is: " + this.finalBookingModel.getCustomer().get().getLoyaltyPoints());

        Label thankLabel = new Label();
        thankLabel.setText("Thank you for using our service!");

        // Check if the bill is eligible for a random gift
        if (this.finalBookingController.totalPrice().get() > this.finalBookingModel.getMinimumPaidForPrize()) {
            Label label = new Label();

            Label prizeLabel = new Label();
    
            label.setText("Your bill is eligible for a random gift.");

            int random = this.finalBookingController.randomPrize();

            prizeLabel.setText("Congratulation! You received the prize: " + this.finalBookingModel.getPrize(random));

            Button exitBtn = new Button("Exit");

            exitBtn.setOnAction(event -> {
                this.endBooking.set(true);
                primaryBookingStage.close();
                stage.close();    
            });        

            VBox altRoot = new VBox(5, totalPriceLabel, paymentResult, new Label(), label, prizeLabel, new Label(), loyalPointsLabel, new Label(), thankLabel, new Label(), exitBtn);
            altRoot.setAlignment(Pos.CENTER);
    
            Scene scene = new Scene(altRoot, 400, 300);
            stage.setScene(scene);
            stage.show();
            return;    
        }

        Button exitBtn = new Button("Exit");
    
        exitBtn.setOnAction(event -> {
            this.endBooking.set(true);
            primaryBookingStage.close();
            stage.close();
        });

        VBox root = new VBox(5, totalPriceLabel, paymentResult, new Label(), loyalPointsLabel, new Label(), thankLabel, new Label(), exitBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.show();
    }

    // Method to configure a TextField to accept only integer values
    private void configTextFieldForInts(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("-?\\d*")) {
                return c;
            }
            return null;
        }));
    }
}
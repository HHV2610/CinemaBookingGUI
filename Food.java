import java.util.Comparator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


// Enum representing different types of snacks with their respective prices
enum SnackType {
    POPCORN(11.90), CHIPS(10), COOKIES(9), FRIES(11);

    private double price;

    SnackType(double price) {
        this.price = price;
    }

    public String getName() {
        return this.name();
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return this.name() + " - Price: " + this.price + "$"; 
    }
}

// Enum representing different types of drinks with their respective prices
enum DrinkType {
    COKE(5.5), SPRITE(5.5), LEMONADE(6), FANTA(5.5), MILK_TEA(7);

    private double price;

    DrinkType(double price) {
        this.price = price;
    }

    public String getName() {
        return this.name();
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return this.name() + " - Price: " + this.price + "$"; 
    }
}

// Class representing a Snack item implementing getPriceAndToTalPrice interface
class Snack implements getPriceAndToTalPrice {
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    SimpleIntegerProperty quantityProperty;
    private SimpleDoubleProperty discountRate;
    private boolean isDiscount;

    // Constructor to initialize Snack object with name and quantity
    Snack(SnackType name, int quantity, double discountRate, boolean isDiscount) {
        this.name = new SimpleStringProperty(name.getName());
        this.price = new SimpleDoubleProperty(name.getPrice());
        this.quantityProperty = new SimpleIntegerProperty(quantity);
        this.discountRate = new SimpleDoubleProperty(discountRate);
        this.isDiscount = isDiscount;
    }

    // Getter method for the name of the snack
    public SimpleStringProperty getName() {
        return this.name;
    }

    // Overridden method to get the price of the snack
    @Override
    public SimpleDoubleProperty getPrice() {
        return this.price;
    }

    public boolean getIsDiscount() {
        return this.isDiscount;
    }

    public SimpleDoubleProperty getDiscountRate() {
        return this.discountRate;
    }

    public SimpleIntegerProperty getQuantity() {
        return this.quantityProperty;
    }

    // Method to set the quantity of the snack
    public void setQuantity(int quantity) {
        this.quantityProperty = new SimpleIntegerProperty(quantity);
    }

    // Overridden method to compute the total price of the snack
    @Override
    public SimpleDoubleProperty computeTotalPrice() {
        if (this.isDiscount) {
            return new SimpleDoubleProperty((this.price.get() * this.quantityProperty.get()) - (this.price.get() * this.quantityProperty.get() * (this.discountRate.get() / 100)));
        }
        return new SimpleDoubleProperty(this.price.get() * this.quantityProperty.get());
    }

    // Overridden toString method to return a string representation of the snack
    public String toString() {
        return "Snack: " + this.getName().get() + " - Quantity: " + this.quantityProperty.get() + " - Total Price: " + this.computeTotalPrice().get();
    }
}

// Class representing a Drink item implementing getPriceAndToTalPrice interface
class Drink implements getPriceAndToTalPrice {
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    SimpleIntegerProperty quantityProperty;
    private SimpleDoubleProperty discountRate;
    private boolean isDiscount;

    // Constructor to initialize Drink object with name and quantity
    Drink(DrinkType name, int quantity, double discountRate, boolean isDiscount) {
        this.name = new SimpleStringProperty(name.getName());
        this.price = new SimpleDoubleProperty(name.getPrice());
        this.quantityProperty = new SimpleIntegerProperty(quantity);
        this.discountRate = new SimpleDoubleProperty(discountRate);
        this.isDiscount = isDiscount;
    }

    // Getter method for the name of the drink
    public SimpleStringProperty getName() {
        return this.name;
    }

    // Overridden method to get the price of the drink
    @Override
    public SimpleDoubleProperty getPrice() {
        return this.price;
    }

    public boolean getIsDiscount() {
        return this.isDiscount;
    }

    public SimpleDoubleProperty getDiscountRate() {
        return this.discountRate;
    }

    public SimpleIntegerProperty getQuantity() {
        return this.quantityProperty;
    }

     // Method to set the quantity of the drink
    public void setQuantity(int quantity) {
        this.quantityProperty = new SimpleIntegerProperty(quantity);
    }

    // Overridden toString method to return a string representation of the drink
    @Override
    public SimpleDoubleProperty computeTotalPrice() {
        if (this.isDiscount) {
            return new SimpleDoubleProperty((this.price.get() * this.quantityProperty.get()) - (this.price.get() * this.quantityProperty.get() * (this.discountRate.get() / 100)));
        }
        return new SimpleDoubleProperty(this.price.get() * this.quantityProperty.get());
    }

    // Overridden toString method to return a string representation of the drink
    public String toString() {
        return "Drink: " + this.getName().get() + " - Quantity: " + this.quantityProperty.get() + " - Total Price: " + this.computeTotalPrice().get();
    }
}

class ComboOffer implements GetPrice {
    private String name;
    private Snack[] snacks;
    private Drink[] drink;
    private double price;

    ComboOffer(String name, Snack[] snacks, Drink[] drink, double price) {
        this.name = name;
        this.snacks = snacks;
        this.drink = drink;
        this.price = price;
    }

    static final Comparator<ComboOffer> comparator = Comparator.comparing(ComboOffer::getName);

    public String getName() {
        return this.name;
    }

    public Snack[] getSnacks() {
        return this.snacks;
    }

    public Drink[] getDrinks() {
        return this.drink;
    }

    public SimpleDoubleProperty getPrice() {
        return new SimpleDoubleProperty(this.price);
    }

    public String listSnack() {
        String list = "Snack:";
        for(Snack s : this.snacks) {
            list += "\n" + s.getName().get() + " - Quantity: " + s.getQuantity().get() + " - Total Price: " + s.computeTotalPrice().get();
        }
        return list;
    }

    public String listDrink() {
        String list = "Drink:";
        for(Drink d : this.drink) {
            list += "\n" + d.getName().get() + " - Quantity: " + d.getQuantity().get() + " - Total Price: " + d.computeTotalPrice().get();
        }
        return list;
    }

    public String toString() {
        return this.name + " - Price: " + this.price + " \n" + this.listSnack() + "\n" + this.listDrink();
    }
}

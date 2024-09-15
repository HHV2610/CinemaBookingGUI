import javafx.beans.property.SimpleStringProperty;

abstract class Person {
    private SimpleStringProperty name;
    private String id;
    private SimpleStringProperty email;
    private SimpleStringProperty phone;

    public Person(SimpleStringProperty name, String id, SimpleStringProperty email, SimpleStringProperty phone) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public SimpleStringProperty getPhone() {
        return phone;
    }

    public abstract String getRole();

    public abstract String getDetails();

    @Override
    public String toString() {
        return "Name: " + name.get() + ", ID: " + id + ", Email: " + email.get() + ", Phone: " + phone.get();
    }
}

class Customer extends Person {
    private int loyaltyPoints;
    private SimpleStringProperty password;

    public Customer(SimpleStringProperty name, String id, SimpleStringProperty email, SimpleStringProperty phone, int loyaltyPoints, SimpleStringProperty password) {
        super(name, id, email, phone);
        this.loyaltyPoints = loyaltyPoints;
        this.password = password;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints += loyaltyPoints;
    }

    public SimpleStringProperty getPassword() {
        return this.password;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public String getDetails() {
        return toString() + ", Loyalty Points: " + loyaltyPoints;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: " + getRole();
    }
}

class Employee extends Person {
    private String position;
    private double salary;

    public Employee(SimpleStringProperty name, String id, SimpleStringProperty email, SimpleStringProperty phone, String position, double salary) {
        super(name, id, email, phone);
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String getRole() {
        return "Employee";
    }

    @Override
    public String getDetails() {
        return toString() + ", Position: " + position + ", Salary: " + salary;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: " + getRole() + ", Position: " + getPosition();
    }
}

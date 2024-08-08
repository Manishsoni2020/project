import java.util.*;

class Bike {
    private String bikeId;
    private String brand;
    private String model;
    private int perDayrent;
    private boolean available;

    public Bike(String bikeId, String brand, String model, int perDayrent) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.perDayrent = perDayrent;
        this.available = true;
    }

    public String getbikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getmodel() {
        return model;
    }

    public double bikeprice(int rentalDays) {
        return perDayrent * rentalDays;
    }

    public boolean available() {
        return available;
    }

    public void rent() {
        available = false;
    }

    public void returnBike() {
        available = true;
    }
}

class Customer {
    private String customerid;
    private String customername;

    public Customer(String customerid, String customername) {
        this.customerid = customerid;
        this.customername = customername;
    }

    public String getcustomerid() {
        return customerid;
    }

    public String getcustomername() {
        return customername;
    }
}

class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getcustomer() {
        return customer;
    }

    public int getdays() {
        return days;
    }
}

class BikeRentalSystem {
    private List<Bike> bikes;
    private List<Customer> customers;
    private List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addbike(Bike bike) {
        bikes.add(bike);
    }

    public void addcustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentbike(Bike bike, Customer customer, int days) {
        if (bike.available()) {
            bike.rent();
            rentals.add(new Rental(bike, customer, days));
        } else {
            System.out.println("Bike is not Available for rent");
        }
    }

    public void returnbike(Bike bike) {
        bike.returnBike();
        Rental rentaltoremove = null;
        for (Rental rental : rentals) {
            if (rental.getBike() == bike) {
                rentaltoremove = rental;
                break;
            }
        }
        if (rentaltoremove != null) {
            rentals.remove(rentaltoremove);
        } else {
            System.out.println("Bike was not rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("------ Bike Rental System -----");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.println("-- Rent a Bike --");
                System.out.println("Enter Your Name ");
                sc.nextLine();
                String name = sc.nextLine();
                System.out.println("Available Bikes");
                for (Bike bike : bikes) {
                    if (bike.available()) {
                        System.out.println(bike.getbikeId() + " - " + bike.getBrand() + " - " + bike.getmodel());
                    }
                }
                System.out.println("Enter the Bike ID you want to rent: ");
                String bikeId = sc.nextLine();
                System.out.println("Enter the Number of Days for Rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), name);
                addcustomer(newCustomer);

                Bike selectedBike = null;
                for (Bike bike : bikes) {
                    if (bike.getbikeId().equals(bikeId) && bike.available()) {
                        selectedBike = bike;
                        break;
                    }
                }
                if(selectedBike !=null)
                {
                    double totalprice=selectedBike.bikeprice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getcustomerid());
                    System.out.println("Customer Name: "+ newCustomer.getcustomername());
                    System.out.println("Bike: "+selectedBike.getBrand()+" "+selectedBike.getmodel());
                    System.out.println("Rental Days: "+rentalDays);
                    System.out.println("Total Price: $%.2f%n"+totalprice);

                    System.out.println("\nConfirm rental (Y/N): ");
                    String confirm=sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentbike(selectedBike, newCustomer, rentalDays);
                        System.out.println("\nBike rented successfully.");
                    }
                    else 
                    {
                        System.out.println("\nRental canceled.");
                    }
                }
                else 
                {
                    System.out.println("\nInvalid Bike selection or bike not available for rent.");
                }
            }
            else if(choice == 2)
            {
                System.out.println("\n== Return a Bike ==\n");
                System.out.println("Enter the bike ID you want to return: ");
                String bikeId=sc.nextLine();
                sc.nextLine();
                Bike bikeToReturn=null;
                for(Bike bike : bikes)
                {
                    if(bike.getbikeId().equals(bikeId) && !bike.available())
                    {
                        bikeToReturn=bike;
                        break;
                    }
                }
                if(bikeToReturn!=null)
                {
                    Customer customer=null;
                    for(Rental rental: rentals)
                    {
                        if(rental.getBike()== bikeToReturn)
                        {
                            customer=rental.getcustomer();
                            break;
                        }
                    }
                    if (customer!=null) {
                        returnbike(bikeToReturn);
                        System.out.println("Bike returned successfully by " + customer.getcustomername());
                    }
                    else 
                    {
                        System.out.println("Bike was not rented or rental information is missing.");
                    }
                }
            }
            else if(choice==3)
            {
                break;
            }
            else 
            {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Main {
    public static void main(String[] args) {
        BikeRentalSystem system = new BikeRentalSystem();

        Bike bike1 = new Bike("B001", "Yamaha", "MT-15", 100);
        Bike bike2 = new Bike("B002", "Honda", "CBR500R", 200);
        Bike bike3 = new Bike("B003", "Kawasaki", "Ninja 300", 150);

        system.addbike(bike1);
        system.addbike(bike2);
        system.addbike(bike3);

        system.menu();
    }
}
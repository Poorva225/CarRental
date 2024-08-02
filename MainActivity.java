// package com.poorva.carrental;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Car{
    private String car_id;
    private String car_brand;
    private String car_model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String car_id, String car_brand, String car_model, double basePricePerDay) {

        this.car_id = car_id;
        this.basePricePerDay = basePricePerDay;
        this.car_model = car_model;
        this.car_brand= car_brand;
        this.isAvailable= true;

    }

    public String getCar_id() {
        return car_id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getCar_model() {
        return car_model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;

    }
}
class Customer{
    private String cust_id;
    private String cust_name;

    public Customer(String cust_id, String cust_name) {
        this.cust_id = cust_id;
        this.cust_name = cust_name;
    }

    public String getCust_id() {
        return cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }


}
class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);

    }
    public void addCustomers(Customer customer){

        customers.add(customer);
    }
    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else{
            System.out.println("Car is Not available for rent");
        }
    }
    public void renturnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove =rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car returned Successfully");
        }
        else {
            System.out.println("Car was Not Rented");
        }
    }
    public void menu(){
        Scanner MyInput =new Scanner(System.in);
        while(true){
            System.out.println("=====CAR RENTAL SYSTEM=====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter Your choice: ");

            int choice = MyInput.nextInt();
            MyInput.nextLine(); // Consume newLine

            if(choice ==1){
                System.out.println("\n ===Rent a Car=== \n");
                System.out.println("Enter Your Name: ");
                String cust_Name = MyInput.nextLine();
                System.out.println("\n Available Cars: ");
                for(Car car: cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCar_id() + " - "+ car.getCar_brand() + " " + car.getCar_model());
                    }
                }
                System.out.println("Enter Car Id that You Want to Rent: ");
                String carId =  MyInput.nextLine();
                System.out.println("Enter the Number of Days For Rental: ");
                int rentalDays = MyInput.nextInt();
                MyInput.nextLine(); // consume line

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), cust_Name);
                addCustomers(newCustomer);

                Car selectedCar = null;
                for(Car car : cars) {
                    if(car.getCar_id().equals(carId) && car.isAvailable()){
                        selectedCar =car;
                        break;
                    }
                }
                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n-----RENTAL INFORMATION-----\n");
                    System.out.println("Customer Id: " + newCustomer.getCust_id());
                    System.out.println("Customer Name: " + newCustomer.getCust_name());
                    System.out.println("Car: " + selectedCar.getCar_brand() + selectedCar.getCar_model());
                    System.out.println("Rental Days: " +rentalDays);
                    System.out.printf("Total Price: %.2f%n", totalPrice);
                    System.out.println("\nConfirm Rental (Y/N): ");
                    String confirm = MyInput.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car Rented Successfully!!");
                    }else{
                        System.out.println("Rental Canceled😕");
                    }
                }else{
                    System.out.println("\nInvalid car Selection or Car Not available for Rent!");
                }
            }
            else if (choice==2){
                System.out.println("\n-----RETURN A CAR-----");
                System.out.println("Enter the car ID that You want to return: ");
                String carId = MyInput.nextLine();

                Car carToReturn = null;
                for(Car car: cars){
                    if (car.getCar_id().equals(carId) && !car.isAvailable()){
                        carToReturn =car;
                        break;
                    }
                }
                if (carToReturn !=null){
                    Customer customer =null;
                    for(Rental rental: rentals){
                        if(rental.getCar()==carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null) {
                        renturnCar(carToReturn);
                        System.out.println("Car Returned aSuccessfully by " +customer.getCust_name());
                    }else{
                        System.out.println("Car was not rented or rental informatin is missing.");
                    }
                }else{
                    System.out.println("Invalid Cr ID or Car is not Rented.");
                }
            } else if(choice == 3){
                break;
            }else{
                System.out.println("Invalid Choice. Please Enter the valid option.");
            }
        }
        System.out.println("Thankyou for using this Car Rental System!\nWe are Delighted.\nVisit Again!!");

    }

}
public class MainActivity{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();
    }
}
package ticket;

import ticket.entities.Train;
import ticket.entities.User;
import ticket.services.UserBookingService;
import ticket.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        System.out.println("Runing train booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService = null;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.out.println("There is something wrong ");
            return;
        }

        while (option != 7) {
            System.out.println("Choose an option");
            System.out.println("1.Sing Up");
            System.out.println("2.Login");
            System.out.println("3.Fetch Booking");
            System.out.println("4.Search Trains");
            System.out.println("5.Book a Seat");
            System.out.println("6.Cancel a Booking");
            System.out.println("7.Exit the App");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();
                    User userToSignup = new User(nameToSignUp, passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignup);
                    break;

                case 2:
                    System.out.println("Enter the username to login");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password to login");
                    String passwordToLogin = scanner.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(userToLogin);
                    } catch (IOException ex) {
                        System.out.println("Login failed: " + ex.getMessage());
                        break;
                    }
                    if (userBookingService.loginUser()) {
                        System.out.println("Login Successful");
                    } else {
                        System.out.println("Login Failed");
                    }
                    break;

                case 3:
                    System.out.println("Fetching your Booking");
                    userBookingService.fetchBookings();
                    break;

                case 4:
                    System.out.println("Searching your soucre station");
                    String sourceStation = scanner.next();
                    System.out.println("search your destination station");
                    String destinationStation = scanner.next();
                    List<Train> train = userBookingService.getTrains(sourceStation, destinationStation);
                    break;
            }
        }
    }
}
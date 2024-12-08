package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public class RentVideoCommand implements Command {
    private final RentalService rentalService;
    private final Scanner scanner;

    public RentVideoCommand(RentalService rentalService, Scanner scanner) {
        this.rentalService = rentalService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        System.out.println("Enter video title to rent: ");
        String videoTitle = scanner.next();

        rentalService.rentVideo(customerName, videoTitle);
    }
}

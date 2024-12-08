package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public class ReturnVideoCommand implements Command {
    private final RentalService rentalService;
    private final Scanner scanner;

    public ReturnVideoCommand(RentalService rentalService, Scanner scanner) {
        this.rentalService = rentalService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        System.out.println("Enter video title to rent: ");
        String videoTitle = scanner.next();

        rentalService.returnVideo(customerName, videoTitle);
    }
}

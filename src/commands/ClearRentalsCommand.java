package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public class ClearRentalsCommand implements Command {
    private final RentalService rentalService;
    private final Scanner scanner;

    public ClearRentalsCommand(RentalService rentalService, Scanner scanner) {
        this.rentalService = rentalService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        rentalService.clearRentals(customerName);
    }
}

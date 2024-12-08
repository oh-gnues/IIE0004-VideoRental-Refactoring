package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public abstract class AbstractRentalCommand implements Command {
    protected final RentalService rentalService;
    protected final Scanner scanner;

    public AbstractRentalCommand(RentalService rentalService, Scanner scanner) {
        this.rentalService = rentalService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        System.out.println("Enter video title: ");
        String videoTitle = scanner.next();

        processRental(customerName, videoTitle);
    }

    protected abstract void processRental(String customerName, String videoTitle);
}

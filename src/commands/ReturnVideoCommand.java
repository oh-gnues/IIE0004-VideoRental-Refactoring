package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public class ReturnVideoCommand extends AbstractRentalCommand {
    public ReturnVideoCommand(RentalService rentalService, Scanner scanner) {
        super(rentalService, scanner);
    }

    @Override
    protected void processRental(String customerName, String videoTitle) {
        rentalService.returnVideo(customerName, videoTitle);
    }
}

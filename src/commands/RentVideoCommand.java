package src.commands;

import src.rental.RentalService;

import java.util.Scanner;

public class RentVideoCommand extends AbstractRentalCommand {
    public RentVideoCommand(RentalService rentalService, Scanner scanner) {
        super(rentalService, scanner);
    }

    @Override
    protected void processRental(String customerName, String videoTitle) {
        rentalService.rentVideo(customerName, videoTitle);
    }
}

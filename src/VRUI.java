package src;

import src.commands.*;
import src.customer.CustomerFormatter;
import src.customer.CustomerReportFormatter;
import src.customer.CustomerService;
import src.rental.RentalService;
import src.video.VideoService;

import java.util.Scanner;

public class VRUI {
	private final Scanner scanner = new Scanner(System.in);

	private final CommandRegistry commandRegistry = new CommandRegistry();

    public VRUI() {
        CustomerFormatter customerFormatter = new CustomerFormatter();
        CustomerReportFormatter customerReportFormatter = new CustomerReportFormatter();
        VideoService videoService = new VideoService();
        CustomerService customerService = new CustomerService();
        RentalService rentalService = new RentalService(customerService, videoService);
        Initializer initializer = new Initializer(customerService, videoService, rentalService);

        commandRegistry.registerCommand(1, new ListCustomersCommand(customerService, customerFormatter));
		commandRegistry.registerCommand(2, new ListVideosCommand(videoService));
		commandRegistry.registerCommand(3, new RegisterCustomerCommand(customerService, scanner));
		commandRegistry.registerCommand(4, new RegisterVideoCommand(videoService, scanner));
		commandRegistry.registerCommand(5, new RentVideoCommand(rentalService, scanner));
		commandRegistry.registerCommand(6, new ReturnVideoCommand(rentalService, scanner));
        commandRegistry.registerCommand(7, new GetCustomerReportCommand(customerService, customerReportFormatter, scanner));
		commandRegistry.registerCommand(8, new ClearRentalsCommand(rentalService, scanner));
        commandRegistry.registerCommand(-1, new InitializerCommand(initializer));
	}

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;
		ui.run();
	}

	public void run() {
		boolean quit = false;
		while (!quit) {
			int commandKey = showCommand();
			Command command = commandRegistry.getCommand(commandKey);

			if (command != null) {
				command.execute();
			} else if (commandKey == 0) {
				quit = true;
			} else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Bye");
	}

	public int showCommand() {
		System.out.println("\nSelect a command !") ;
		System.out.println("\t 0. Quit") ;
		System.out.println("\t 1. List customers") ;
		System.out.println("\t 2. List videos") ;
		System.out.println("\t 3. Register customer") ;
		System.out.println("\t 4. Register video") ;
		System.out.println("\t 5. Rent video") ;
		System.out.println("\t 6. Return video") ;
		System.out.println("\t 7. Show customer report") ;
		System.out.println("\t 8. Show customer and clear rentals") ;

		return scanner.nextInt() ;
	}
}

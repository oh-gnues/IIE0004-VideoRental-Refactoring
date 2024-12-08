package src;

import java.util.Scanner;

public class VRUI {
	private final Scanner scanner ;

	private final CustomerService customerService = new CustomerService() ;
	private final VideoService videoService = new VideoService() ;
	private final RentalService rentalService = new RentalService(customerService, videoService) ;
	private final ReportFormatter reportFormatter = new ReportFormatter();
	private final CustomerFormatter customerFormatter = new CustomerFormatter();

	public VRUI(Scanner scanner) {
		this.scanner = scanner;
	}
	public VRUI() {
		this(new Scanner(System.in));
	}

	public void run() {
		boolean quit = false;
		while (!quit) {
			int command = showCommand();
			quit = processCommand(command);
		}
		System.out.println("Bye");
	}

	public boolean processCommand(int command) {
		switch (command) {
			case 0: return true;
			case 1: listCustomers() ; break ;
			case 2: listVideos() ; break ;
			case 3: registerCustomer() ; break ;
			case 4: registerVideo() ; break ;
			case 5: rentVideo() ; break ;
			case 6: returnVideo() ; break ;
			case 7: getCustomerReport() ; break;
			case 8: clearRentals() ; break ;
			case -1: init() ; break ;
		}
		return false;
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

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;
		ui.run();
	}

	public void listCustomers() {
		System.out.println("List of customers") ;
		var customers = customerService.getAllCustomers();
		var customerDetails = customerFormatter.formatCustomers(customers);
		for ( String detail : customerDetails ) {
			System.out.println(detail) ;
		}
		System.out.println("End of list") ;
	}

	public void listVideos() {
		System.out.println("List of videos") ;

		for ( String videoInfo: videoService.listVideos() ) {
			System.out.println(videoInfo) ;
		}
		System.out.println("End of list") ;
	}

	public void registerCustomer() {
		System.out.println("Enter customer name: ") ;
		String name = scanner.next() ;

		customerService.registerCustomer(name) ;
	}

	public void registerVideo() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt() ;

		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt() ;

		videoService.registerVideo(title, videoType, priceCode) ;
	}

	public void rentVideo() {
		System.out.println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;

		rentalService.rentVideo(customerName, videoTitle);
	}

	public void returnVideo() {
		System.out.println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		System.out.println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;

		rentalService.returnVideo(customerName, videoTitle) ;
	}

	public void getCustomerReport() {
		System.out.println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		CustomerReportData reportData = rentalService.getCustomerReportData(customerName);
		if (reportData == null) {
			System.out.println("No customer found.");
		} else {
			String report = reportFormatter.formatCustomerReport(reportData);
			System.out.println(report);
		}
	}

	public void clearRentals() {
		System.out.println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		rentalService.clearRentals(customerName) ;
	}

	private void init() {
		customerService.initDefaultCustomers() ;
		videoService.initDefaultVideos() ;
		rentalService.initDefaultRentals() ;
	}
}

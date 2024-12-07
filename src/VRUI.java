package src;

import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private CustomerService customerService = new CustomerService() ;
	private VideoService videoService = new VideoService() ;
	private RentalService rentalService = new RentalService(customerService, videoService) ;
	
	public static void main(String[] args) {
		VRUI ui = new VRUI() ;
		
		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.registerCustomer() ; break ;
				case 4: ui.registerVideo() ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break ;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye") ;
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

	public void listCustomers() {
		System.out.println("List of customers") ;
		for ( String customerDetail: customerService.listCustomers() ) {
			System.out.println(customerDetail) ;
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

		System.out.println(rentalService.getCustomerReport(customerName));
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

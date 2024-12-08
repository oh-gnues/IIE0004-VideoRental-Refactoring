package src;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}

	public void removeRental(Rental rental) {
		rentals.remove(rental);
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = each.getCharge();
			int eachPoint = each.getPoints();

			result += "\t" + each.getVideo().getTitle()
					+ "\tDays rented: " + each.getDaysRented()
					+ "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		return result+couponMessage(totalPoint) ;
	}

	private String couponMessage(int totalPoint) {
		String message = "";

		if (totalPoint >= 10) {
			message += "Congrat! You earned one free coupon\n";
		}
		if (totalPoint >= 30) {
			message += "Congrat! You earned two free coupons\n";
		}

		return message;
	}
}

package src;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private List<Rental> rentals = new ArrayList<>();

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

	public CustomerReportData generateReportData() {
		CustomerReportData reportData = new CustomerReportData(getName());

		for (Rental each : rentals) {
			double eachCharge = each.getCharge();
			int eachPoint = each.getPoints();
			int daysRented = each.getDaysRented();

			reportData.addRentalData(new CustomerReportData.RentalData(
					each.getVideo().getTitle(),
					daysRented,
					eachCharge,
					eachPoint
			));

			reportData.addCharge(eachCharge);
			reportData.addPoint(eachPoint);
		}

		return reportData;
	}
}

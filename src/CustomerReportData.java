package src;

import java.util.ArrayList;
import java.util.List;

public class CustomerReportData {
    private final String customerName;
    private double totalCharge;
    private int totalPoint;
    private final List<RentalData> rentalsData = new ArrayList<>();

    public CustomerReportData(String customerName) {
        this.customerName = customerName;
    }

    public void addRentalData(RentalData data) {
        rentalsData.add(data);
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void addCharge(double charge) {
        this.totalCharge += charge;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void addPoint(int point) {
        this.totalPoint += point;
    }

    public List<RentalData> getRentalsData() {
        return rentalsData;
    }

    public static class RentalData {
        private final String title;
        private final int daysRented;
        private final double charge;
        private final int point;

        public RentalData(String title, int daysRented, double charge, int point) {
            this.title = title;
            this.daysRented = daysRented;
            this.charge = charge;
            this.point = point;
        }

        public String getTitle() {
            return title;
        }

        public int getDaysRented() {
            return daysRented;
        }

        public double getCharge() {
            return charge;
        }

        public int getPoint() {
            return point;
        }
    }

}

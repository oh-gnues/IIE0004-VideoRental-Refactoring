package src.customer;

public class CustomerReportFormatter {
    public String formatCustomerReport(CustomerReportData reportData) {
        StringBuilder result = new StringBuilder();
        result.append("Customer Report for ").append(reportData.getCustomerName()).append("\n");

        for (CustomerReportData.RentalData rentalData : reportData.getRentalsData()) {
            result.append("\t")
                    .append(rentalData.getTitle())
                    .append("\tDays rented: ")
                    .append(rentalData.getDaysRented())
                    .append("\tCharge: ")
                    .append(rentalData.getCharge())
                    .append("\tPoint: ")
                    .append(rentalData.getPoint())
                    .append("\n");
        }

        result.append("Total charge: ").append(reportData.getTotalCharge())
                .append("\tTotal Point:").append(reportData.getTotalPoint())
                .append("\n");

        result.append(couponMessage(reportData.getTotalPoint()));

        return result.toString();
    }

    private String couponMessage(int totalPoint) {
        StringBuilder message = new StringBuilder();

        if (totalPoint >= 10) {
            message.append("Congrat! You earned one free coupon\n");
        }
        if (totalPoint >= 30) {
            message.append("Congrat! You earned two free coupons\n");
        }

        return message.toString();
    }
}

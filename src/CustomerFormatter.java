package src;

import java.util.ArrayList;
import java.util.List;

public class CustomerFormatter {
    public List<String> formatCustomers(List<Customer> customers) {
        List<String> customerDetails = new ArrayList<>();
        for (Customer customer : customers) {
            StringBuilder customerInfo = new StringBuilder();
            customerInfo.append("Name: ").append(customer.getName())
                    .append("\tRentals: ").append(customer.getRentals().size());

            for (Rental rental : customer.getRentals()) {
                customerInfo.append("\n\tTitle: ").append(rental.getVideo().getTitle())
                        .append(" \tPrice Code: ").append(rental.getVideo().getPriceCode());
            }

            customerDetails.add(customerInfo.toString());
        }
        return customerDetails;
    }
}
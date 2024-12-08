package src;

import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<Customer>();
    }

    public List<String> listCustomers() {
        List<String> customerDetails = new ArrayList<>();
        for (Customer customer : customers) {
            StringBuilder customerInfo = new StringBuilder();
            customerInfo.append("Name: ").append(customer.getName()).append("\tRentals: ").append(customer.getRentals().size());
            for (Rental rental : customer.getRentals()) {
                customerInfo.append("\n\tTitle: " + rental.getVideo().getTitle())
                        .append(" \tPrice Code: ").append(rental.getVideo().getPriceCode());
            }
            customerDetails.add(customerInfo.toString());
        }
        return customerDetails;
    }

    public void registerCustomer(String name) {
        customers.add(new Customer(name));
    }

    public Customer findCustomer(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void initDefaultCustomers() {
        customers.add(new Customer("James"));
        customers.add(new Customer("Brown"));
    }
}

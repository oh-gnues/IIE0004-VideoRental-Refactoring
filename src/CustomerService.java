package src;

import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<Customer>();
    }

    public List<String> listCustomers() {
        List<String> customerInfo = new ArrayList<>();
        for (Customer customer : customers) {
            customerInfo.add("Name: " + customer.getName() + ", Rentals: " + customer.getRentals().size());
        }
        return customerInfo;
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

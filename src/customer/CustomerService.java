package src.customer;

import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    private final List<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<>();
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
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

    public CustomerReportData getCustomerReportData(String customerName) {
        Customer customer = findCustomer(customerName);
        return (customer != null) ? customer.generateReportData() : null;
    }
}

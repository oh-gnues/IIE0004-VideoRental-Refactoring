package src.commands;

import src.customer.CustomerFormatter;
import src.customer.CustomerService;

public class ListCustomersCommand implements Command {
    private final CustomerService customerService;
    private final CustomerFormatter customerFormatter;

    public ListCustomersCommand(CustomerService customerService, CustomerFormatter customerFormatter) {
        this.customerService = customerService;
        this.customerFormatter = customerFormatter;
    }

    @Override
    public void execute() {
        System.out.println("List of customers") ;
        var customers = customerService.getAllCustomers();
        var customerDetails = customerFormatter.formatCustomers(customers);
        for ( String detail : customerDetails ) {
            System.out.println(detail);
        }
        System.out.println("End of list");
    }
}

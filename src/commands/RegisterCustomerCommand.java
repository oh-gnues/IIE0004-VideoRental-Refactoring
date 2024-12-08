package src.commands;

import src.customer.CustomerService;

import java.util.Scanner;

public class RegisterCustomerCommand implements Command {
    private final CustomerService customerService;
    private final Scanner scanner;

    public RegisterCustomerCommand(CustomerService customerService, Scanner scanner) {
        this.customerService = customerService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String name = scanner.next();

        customerService.registerCustomer(name);
    }
}

package src.commands;

import src.customer.CustomerReportData;
import src.customer.CustomerService;
import src.customer.CustomerReportFormatter;

import java.util.Scanner;

public class GetCustomerReportCommand implements Command {
    private final CustomerService customerService;
    private final CustomerReportFormatter customerReportFormatter;
    private final Scanner scanner;

    public GetCustomerReportCommand(CustomerService customerService, CustomerReportFormatter customerReportFormatter, Scanner scanner) {
        this.customerService = customerService;
        this.customerReportFormatter = customerReportFormatter;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter customer name: ");
        String customerName = scanner.next();

        CustomerReportData reportData = customerService.getCustomerReportData(customerName);
        if (reportData == null) {
            System.out.println("No customer found.");
        } else {
            String report = customerReportFormatter.formatCustomerReport(reportData);
            System.out.println(report);
        }
    }
}

import java.io.File;
import java.util.Scanner;

public class TicketCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;
        String defaultConfigPath = "config.json"; // Default file path for the configuration file

        System.out.println("Welcome to the Real-Time Event Ticketing System!\n");

        // Ask user whether to import an existing configuration
        System.out.println("Do you want to import the default configuration file? (yes/no)");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {
            // Attempt to load the default configuration file
            File configFile = new File(defaultConfigPath);
            if (configFile.exists() && configFile.isFile()) {
                config = Configuration.loadFromFile(defaultConfigPath);
                if (config == null) {
                    System.out.println("Failed to load configuration. Exiting program.");
                    return;
                }
                System.out.println("\nLoaded Configuration:");
            } else {
                System.out.println("Default configuration file not found. You need to create a new configuration.");
                config = createNewConfiguration(scanner);
                config.saveToFile(defaultConfigPath);
                System.out.println("New configuration saved to " + defaultConfigPath);
            }
        } else {
            // Create a new configuration
            config = createNewConfiguration(scanner);
            config.saveToFile(defaultConfigPath);
            System.out.println("Configuration saved to " + defaultConfigPath);
        }

        // Display the configuration details
        System.out.println("\nConfiguration Details:");
        System.out.printf("Total Tickets: %d%n", config.getTotalTickets());
        System.out.printf("Ticket Release Rate: %d tickets/sec%n", config.getTicketReleaseRate());
        System.out.printf("Customer Retrieval Rate: %d tickets/sec%n", config.getCustomerRetrievalRate());
        System.out.printf("Maximum Ticket Capacity: %d%n", config.getMaxTicketCapacity());

        System.out.println("\nCreating vendors...");
        int numberOfVendors = getValidInput(scanner, "Enter the number of vendors: ");
        int ticketsPerVendor = getValidInput(scanner, "Enter the number of tickets each vendor will release: ");

        TicketPool ticketPool = new TicketPool(); // Shared ticket pool

        Thread[] vendorThreads = new Thread[numberOfVendors];
        for (int i = 0; i < numberOfVendors; i++) {
            vendorThreads[i] = new Thread(new Vendor(ticketPool, ticketsPerVendor), "Vendor-" + (i + 1));
            vendorThreads[i].start();
        }

        // Wait for all vendor threads to complete
        for (Thread vendorThread : vendorThreads) {
            try {
                vendorThread.join();
            } catch (InterruptedException e) {
                System.out.println("Vendor thread interrupted.");
            }
        }

        System.out.println("\nCreating customers...");
        int numberOfCustomers = getValidInput(scanner, "Enter the number of customers: ");

        Thread[] customerThreads = new Thread[numberOfCustomers];
        for (int i = 0; i < numberOfCustomers; i++) {
            customerThreads[i] = new Thread(new Customer(ticketPool), "Customer-" + (i + 1));
            customerThreads[i].start();
        }

        // Wait for all customer threads to complete
        for (Thread customerThread : customerThreads) {
            try {
                customerThread.join();
            } catch (InterruptedException e) {
                System.out.println("Customer thread interrupted.");
            }
        }

        scanner.close();
    }

    private static Configuration createNewConfiguration(Scanner scanner) {
        int totalTickets = getValidInput(scanner, "Enter the total number of tickets: ");
        int ticketReleaseRate = getValidInput(scanner, "Enter the ticket release rate (tickets/sec): ");
        int customerRetrievalRate = getValidInput(scanner, "Enter the customer retrieval rate (tickets/sec): ");
        int maxTicketCapacity = getValidInput(scanner, "Enter the maximum ticket capacity: ");

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    private static int getValidInput(Scanner scanner, String prompt) {
        int input = -1;
        while (input <= 0) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                if (input <= 0) {
                    System.out.println("Value must be greater than 0. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return input;
    }
}

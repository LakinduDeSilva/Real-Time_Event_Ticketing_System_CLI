import java.io.File;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;
        String defaultConfigPath = "config.json";// Default JSON config
        String textFilePath = "config.txt";// Default text config

        // Welcome message
        System.out.println("____________________________________________________________________");
        System.out.println("|         Welcome to the Real-Time Event Ticketing System!         |");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n");

        // Load or create configuration
        String choice;
        do {
            System.out.print("Do you want to import the default configuration file? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();

            if (!choice.equals("yes") && !choice.equals("no")) {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        } while (!choice.equals("yes") && !choice.equals("no"));

        if (choice.equals("yes")) {
            // Try loading config file
            File configFile = new File(defaultConfigPath);
            if (configFile.exists() && configFile.isFile()) {
                config = Configuration.loadFromFile(defaultConfigPath);
                if (config == null) {
                    System.out.println("Failed to load configuration. Exiting program.");
                    return;
                }
                System.out.println("Loaded Configuration:");
            } else {
                // Create new config if file not found
                System.out.println("Default configuration file not found. You need to create a new configuration.");
                config = createNewConfiguration(scanner);
                config.saveToFile(defaultConfigPath);
                config.saveToTextFile(textFilePath);
                logger.info("New configuration saved to " + defaultConfigPath);
            }
        } else {
            // Create new config
            config = createNewConfiguration(scanner);
            config.saveToFile(defaultConfigPath);
            config.saveToTextFile(textFilePath);
            logger.info("Configuration saved to " + defaultConfigPath);
        }

        // Display configuration details
        System.out.println("\n‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        System.out.println("Configuration Details:");
        System.out.printf("Total Tickets: %d%n", config.getTotalTickets());
        System.out.printf("Ticket Release Rate: %d Tickets/Milli-Seconds%n", config.getTicketReleaseRate());
        System.out.printf("Customer Retrieval Rate: %d Tickets/Milli-Seconds%n", config.getCustomerRetrievalRate());
        System.out.printf("Maximum Ticket Capacity: %d%n", config.getMaxTicketCapacity());
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        System.out.println("\nStarting Ticketing System...");

        // Initialize ticket pool and participants
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        Vendor vendor1 = new Vendor(1,ticketPool,config.getTicketReleaseRate(),config.getTotalTickets());
        Vendor vendor2 = new Vendor(2,ticketPool,config.getTicketReleaseRate(),config.getTotalTickets());
        Customer customer1 = new Customer(ticketPool, config.getCustomerRetrievalRate(), 1);
        Customer customer2 = new Customer(ticketPool, config.getCustomerRetrievalRate(), 2);

        // Start threads
        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);

        vendorThread1.start();
        vendorThread2.start();
        customerThread1.start();
        customerThread2.start();

        // Wait for threads to finish
        try {
            vendorThread1.join();
            vendorThread2.join();
            customerThread1.interrupt();
            customerThread2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    // Create new configuration with user input
    private static Configuration createNewConfiguration(Scanner scanner) {
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n");
        int totalTickets = getValidInput(scanner, "Enter the total number of tickets: ");
        int ticketReleaseRate = getValidInput(scanner, "Enter the ticket release rate (Tickets/Milli-Seconds): ");
        int customerRetrievalRate = getValidInput(scanner, "Enter the customer retrieval rate (Tickets/Milli-Seconds): ");
        int maxTicketCapacity = getValidInput(scanner, "Enter the maximum ticket capacity: ");

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    // Validate numeric input
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

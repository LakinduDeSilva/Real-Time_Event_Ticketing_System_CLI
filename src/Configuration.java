import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

/**
 * Manages system configuration settings and supports saving/loading to/from files.
 */
public class Configuration {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    /**
     * Constructs a Configuration instance with specified parameters.
     *
     * @param totalTickets         Total number of tickets.
     * @param ticketReleaseRate    Rate at which tickets are released (ms).
     * @param customerRetrievalRate Rate at which customers retrieve tickets (ms).
     * @param maxTicketCapacity    Maximum capacity of the ticket pool.
     */
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Gets the total number of tickets.
     *
     * @return Total number of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Gets the rate at which tickets are released.
     *
     * @return Ticket release rate in milliseconds.
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Gets the rate at which customers retrieve tickets.
     *
     * @return Customer retrieval rate in milliseconds.
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Gets the maximum capacity of the ticket pool.
     *
     * @return Maximum ticket capacity.
     */
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    /**
     * Saves the configuration to a JSON file.
     *
     * @param filename Name of the file to save the configuration.
     */
    public void saveToFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("\n‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer); // Serialize and save as JSON
            logger.info("Configuration successfully saved to " + filename);
        } catch (IOException e) {
            logger.severe("Error saving configuration: " + e.getMessage());
        }
    }

    /**
     * Loads the configuration from a JSON file.
     *
     * @param filename Name of the file to load the configuration from.
     * @return Loaded Configuration instance, or null if an error occurs.
     */
    public static Configuration loadFromFile(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Configuration config = gson.fromJson(reader, Configuration.class); // Deserialize from JSON
            logger.info("Configuration successfully loaded from " + filename);
            return config;
        } catch (IOException e) {
            logger.severe("Error loading configuration from file: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves configuration details to a plain text file.
     *
     * @param fileName Name of the text file.
     */
    public void saveToTextFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) { // 'false' overwrites the file
            writer.write("Configuration Details:\n");
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Ticket Release Rate: " + ticketReleaseRate + " ms\n");
            writer.write("Customer Retrieval Rate: " + customerRetrievalRate + " ms\n");
            writer.write("Max Ticket Capacity: " + maxTicketCapacity + "\n");
            logger.info("Configuration saved to " + fileName);
        } catch (IOException e) {
            logger.severe("Error saving configuration to text file: " + e.getMessage());
        }
    }
}
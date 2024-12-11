import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

public class Configuration {
    private final int totalTickets;// Total tickets available
    private final int ticketReleaseRate;// Rate of ticket release (ms)
    private final int customerRetrievalRate;// Rate of ticket retrieval (ms)
    private final int maxTicketCapacity;// Maximum ticket pool size
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    // Constructor
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Getters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    // Save to JSON file
    public void saveToFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("\n‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer);// Serialize and save as JSON
            logger.info("Configuration successfully saved to " + filename);
        } catch (IOException e) {
            logger.severe("Error saving configuration: " + e.getMessage());
        }
    }

    // Load from JSON file
    public static Configuration loadFromFile(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Configuration config = gson.fromJson(reader, Configuration.class);// Deserialize from JSON
            logger.info("Configuration successfully loaded from " + filename);
            return config;
        } catch (IOException e) {
            logger.severe("Error loading configuration from file: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    // Save to  text file
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

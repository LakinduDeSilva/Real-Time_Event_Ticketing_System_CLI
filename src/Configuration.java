import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Constructor for the Configuration class
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

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
            gson.toJson(this, writer);
            System.out.println("Configuration successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Load from JSON file
    public static Configuration loadFromFile(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Configuration config = gson.fromJson(reader, Configuration.class);
            System.out.println("Configuration successfully loaded from " + filename);
            return config;
        } catch (IOException e) {
            System.err.println("Error loading configuration from file: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    // Save configuration details to a text file
    public void saveToTextFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) { // 'false' overwrites the file
            writer.write("Configuration Details:\n");
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Ticket Release Rate: " + ticketReleaseRate + " ms\n");
            writer.write("Customer Retrieval Rate: " + customerRetrievalRate + " ms\n");
            writer.write("Max Ticket Capacity: " + maxTicketCapacity + "\n");
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving configuration to text file: " + e.getMessage());
        }
    }
}

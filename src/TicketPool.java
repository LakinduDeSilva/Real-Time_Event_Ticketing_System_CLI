import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class TicketPool {
    private final List<Integer> tickets; // List of tickets
    private final int maxCapacity;// Max tickets allowed
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    // Constructor
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    // Add a ticket to the pool
    public synchronized void addTickets(int ticket, int vendorId){
        while (tickets.size() >= maxCapacity) {
            try {
                logger.info("TicketPool is full. Vendor " + vendorId + " is waiting to add Ticket " + ticket);
                wait(); // Wait for space
            } catch (InterruptedException e) {
                logger.warning("Vendor " +vendorId+ "interrupted while waiting: " + e.getMessage());
            }
        }
        tickets.add(ticket);// Add ticket
        logger.info("Ticket " +ticket+ " added by Vendor " +vendorId+ "     | Remaining Total Tickets: " + tickets.size());
        notifyAll(); // Notify customers
    }

    // Remove a ticket from the pool
    public synchronized int removeTicket(int customerId) {
        while (tickets.isEmpty()) {
            try {
                if (Vendor.allReleased==true & tickets.isEmpty()){
                    System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n");
                    System.out.println("Ticketing process completed. All Tickets sold");
                    System.exit(0);// Exit if no tickets
                }else {
                    logger.info("No tickets available. Waiting for tickets...");
                    wait();// Wait for tickets
                }
            } catch (InterruptedException e) {
                logger.warning("Customer interrupted: " + e.getMessage());
                break;
            }
        }
        int ticket = tickets.remove(0);//Remove ticket
        logger.info("Ticket " +ticket+ " bought by Customer " +customerId+ "  | Remaining Total Tickets: " + tickets.size());
        notifyAll(); // Notify vendors
        return ticket;
    }
}

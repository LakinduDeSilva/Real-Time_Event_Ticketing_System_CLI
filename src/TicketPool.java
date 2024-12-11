import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * The TicketPool class manages a synchronized pool of tickets that vendors can add to and customers can purchase from.
 * It ensures thread safety and manages capacity constraints.
 */
public class TicketPool {
    private final List<Integer> tickets; // List of tickets
    private final int maxCapacity;// Max tickets allowed
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    /**
     * Constructor for TicketPool.
     * @param maxCapacity The maximum capacity of tickets that can be held in the pool.
     */
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Adds a ticket to the pool. If the pool is full, the method waits until space is available.
     * @param ticket The ticket number to add.
     * @param vendorId The ID of the vendor adding the ticket.
     */
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

    /**
     * Removes a ticket from the pool. If no tickets are available, the method waits until a ticket is added.
     * If all vendors have released their tickets and the pool is empty, the process ends.
     * @param customerId The ID of the customer purchasing the ticket.
     */
    public synchronized void removeTicket(int customerId) {
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
        int ticket = tickets.removeFirst();//Remove ticket
        logger.info("Ticket " +ticket+ " bought by Customer " +customerId+ "  | Remaining Total Tickets: " + tickets.size());
        notifyAll(); // Notify vendors
    }
}

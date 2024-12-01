import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class TicketPool {
    private final List<Integer> tickets; // Use ArrayList
    private final int maxCapacity;
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void addTickets(int ticket, int vendorId){
        while (tickets.size() >= maxCapacity) {
            try {
                logger.info("TicketPool is full. Vendor " + vendorId + " is waiting to add Ticket " + ticket);
                wait(); // Wait until there is space in the pool
            } catch (InterruptedException e) {
                logger.warning("Vendor " +vendorId+ "interrupted while waiting: " + e.getMessage());
            }
        }
        tickets.add(ticket);
        logger.info("Ticket " +ticket+ " added by Vendor " +vendorId+ "     | Remaining Total Tickets: " + tickets.size());
        notifyAll(); // Notify consumers that a ticket is available
    }

    public synchronized int removeTicket(int customerId) {
        while (tickets.isEmpty()) {
            try {
                if (Vendor.allReleased==true & tickets.isEmpty()){
                    System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n");
                    System.out.println("Ticketing process completed. All Tickets sold");
                    System.exit(0);
                }else {
                    logger.info("No tickets available. Waiting for tickets...");
                    wait();
                }// Wait until a ticket is added
            } catch (InterruptedException e) {
                logger.warning("Customer interrupted: " + e.getMessage());
                break;
            }
        }
        int ticket = tickets.remove(0);
        logger.info("Ticket " +ticket+ " bought by Customer " +customerId+ "  | Remaining Total Tickets: " + tickets.size());
        notifyAll(); // Notify producers that space is available
        return ticket;
    }
}

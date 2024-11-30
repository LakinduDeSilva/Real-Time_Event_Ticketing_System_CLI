import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class TicketPool {
    private final List<String> tickets; // Use ArrayList instead of LinkedList
    private final int maxCapacity;
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized boolean addTickets(String ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            logger.info("Ticket added: " + ticket + " | Total Tickets: " + tickets.size());
            notifyAll(); // Notify consumers that a ticket is available
            return true;
        } else {
            logger.info("TicketPool is full. Cannot add more tickets.");
            return false;
        }
    }

    public synchronized String removeTicket() {
        while (tickets.isEmpty()) {
            try {
                if (Vendor.allReleased==true & tickets.isEmpty()){
                    logger.info("Ticketing process completed. All Tickets sold");
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
        String ticket = tickets.remove(0);
        logger.info("Ticket removed: " + ticket+" | Remaining Tickets: " + tickets.size());
        notifyAll(); // Notify producers that space is available
        return ticket;
    }
}

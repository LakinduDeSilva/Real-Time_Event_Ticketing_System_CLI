import java.util.logging.*;

/**
 * Vendor class responsible for releasing tickets into the pool.
 */
public class Vendor implements Runnable {
    private final int vendorId;
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int totalTickets;
    private static int currentTicketId = 1;// Global ticket counter
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private static final Object lock = new Object();
    static Boolean allReleased = false;

    /**
     * Constructs a Vendor instance.
     *
     * @param vendorId           Unique ID of the vendor.
     * @param ticketPool         Shared ticket pool.
     * @param ticketReleaseRate  Rate of ticket release (ms).
     * @param totalTickets       Total tickets to be released.
     */
    public Vendor(int vendorId, TicketPool ticketPool, int ticketReleaseRate, int totalTickets) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
    }

    /**
     * Gets the current ticket ID and increments the global counter.
     *
     * @return The current ticket ID.
     */
    public static synchronized int getCurrentTicketId() {
        return currentTicketId++;
    }

    @Override
    public void run() {
        while (currentTicketId<=totalTickets){
            synchronized (lock) {
                try {
                    Thread.sleep(ticketReleaseRate);// Delay before releasing ticket
                    int ticket = Vendor.getCurrentTicketId();
                    ticketPool.addTickets(ticket, vendorId);// Add ticket to the pool
                    lock.notifyAll();// Notify other vendors
                    if (currentTicketId <= totalTickets) {
                        lock.wait(); // Wait for other vendors
                    }
                } catch (InterruptedException e) {
                    logger.warning("Vendor " + vendorId + " interrupted: " + e.getMessage());
                } catch (Exception e) {
                    logger.severe("Error in Vendor " + vendorId + " thread: " + e.getMessage());
                }
            }
        }
        changeAllReleased();// Mark as completed

    }
    /**
     * Marks all tickets as released.
     */
    public void changeAllReleased(){
        System.out.println("Vendor "+vendorId + " has released all tickets.");
        allReleased=true;
    }

}

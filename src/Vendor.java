import java.util.logging.*;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int totalTickets;
    private final String vendorName;
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
    static Boolean allReleased=false;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTickets, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
        this.vendorName = vendorName;
    }

    public static Boolean getAllReleased() {
        return allReleased;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            try {
                Thread.sleep(ticketReleaseRate);
                String ticket = vendorName + "-Ticket-" + i;
                ticketPool.addTickets(ticket);
            } catch (InterruptedException e) {
                logger.warning("Vendor interrupted: " + e.getMessage());

            } catch (Exception e) {
                logger.severe("Error in Vendor thread: " + e.getMessage());
            }
        }
        changeAllReleased();

    }
    public void changeAllReleased(){
        logger.info(vendorName + " has released all tickets.");
        allReleased=true;
    }

}

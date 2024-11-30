import java.util.logging.*;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    public Customer(TicketPool ticketPool, int customerRetrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(customerRetrievalRate);
                String ticket = ticketPool.removeTicket();
                logger.info(customerName + " purchased: " + ticket);
            } catch (InterruptedException e) {
                logger.warning("Customer interrupted: " + e.getMessage());

            } catch (Exception e) {
            logger.severe("Error in Customer thread: " + e.getMessage());
        }
        }
    }
}

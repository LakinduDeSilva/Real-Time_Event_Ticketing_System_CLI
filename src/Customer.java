import java.util.logging.*;

/**
 * Customer class responsible for retrieving tickets from the pool.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int customerId;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private static final Object lock = new Object();

    /**
     * Constructs a Customer instance.
     *
     * @param ticketPool            Shared ticket pool.
     * @param customerRetrievalRate Rate of ticket retrieval (ms).
     * @param customerId            Unique ID of the customer.
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while (true) {// Infinite loop for continuous ticket retrieval
            synchronized (lock){
                try {
                    Thread.sleep(customerRetrievalRate);// Delay before retrieving ticket
                    ticketPool.removeTicket(customerId);// Remove a ticket from the pool
                    lock.notifyAll();// Notify other customers
                    lock.wait();// Wait for other customers
                } catch (InterruptedException e) {
                    logger.warning("Customer" +customerId+ " interrupted: " + e.getMessage());

                } catch (Exception e) {
                logger.severe("Error in Customer" +customerId+ " thread: " + e.getMessage());
                }
            }
        }
    }
}

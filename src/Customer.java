public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(customerRetrievalRate);
                String ticket = ticketPool.removeTicket();
                System.out.println(customerName + " purchased: " + ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

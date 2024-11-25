public class Customer implements Runnable {
    private final TicketPool ticketPool; // Shared ticket pool

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200); // Simulate time taken to retrieve a ticket
                boolean success = ticketPool.removeTicket();
                if (success) {
                    System.out.println(Thread.currentThread().getName() + " purchased a ticket. Remaining tickets: " + ticketPool.getTicketCount());
                } else {
                    System.out.println(Thread.currentThread().getName() + " found no tickets left.");
                    break; // Exit the loop when no tickets are available
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
            }
        }
    }
}

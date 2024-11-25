public class Vendor implements Runnable {
    private final TicketPool ticketPool; // Shared ticket pool
    private final int ticketsToRelease; // Number of tickets this vendor will release

    public Vendor(TicketPool ticketPool, int ticketsToRelease) {
        this.ticketPool = ticketPool;
        this.ticketsToRelease = ticketsToRelease;
    }

    @Override
    public void run() {
        for (int i = 0; i < ticketsToRelease; i++) {
            try {
                Thread.sleep(100); // Simulate time taken to release tickets
                ticketPool.addTickets(1); // Add one ticket to the pool
                System.out.println(Thread.currentThread().getName() + " added a ticket. Total tickets: " + ticketPool.getTicketCount());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
            }
        }
    }
}

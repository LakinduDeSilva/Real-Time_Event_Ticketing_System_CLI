public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int totalTickets;
    private final String vendorName;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTickets, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets+1; i++) {
            try {
                Thread.sleep(ticketReleaseRate);
                String ticket = vendorName + "-Ticket-" + i;
                ticketPool.addTickets(ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(vendorName + " has released all tickets.");
    }
}

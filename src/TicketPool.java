import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized boolean addTicket(String ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            System.out.println("Ticket added: " + ticket + " | Total Tickets: " + tickets.size());
            notifyAll(); // Notify consumers that a ticket is available
            return true;
        } else {
            System.out.println("TicketPool is full. Cannot add more tickets.");
            return false;
        }
    }

    public synchronized String removeTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("No tickets available. Waiting for tickets...");
                wait(); // Wait until a ticket is added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String ticket = tickets.remove(0);
        System.out.println("Ticket removed: " + ticket + " | Remaining Tickets: " + tickets.size());
        notifyAll(); // Notify producers that space is available
        return ticket;
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}

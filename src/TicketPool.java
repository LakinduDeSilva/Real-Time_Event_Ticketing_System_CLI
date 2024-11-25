import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private final List<Integer> tickets = Collections.synchronizedList(new LinkedList<>());

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            tickets.add(1); // Representing a ticket as an integer
        }
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }

    public synchronized boolean removeTicket() {
        if (!tickets.isEmpty()) {
            tickets.remove(0); // Remove a ticket
            return true;
        } else {
            return false;
        }
    }
}
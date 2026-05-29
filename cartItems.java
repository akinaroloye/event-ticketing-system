
public class cartItems {
	private LiveEvent event;
	private int quantity;
	
	public cartItems(LiveEvent event, int quantity) { //constructor using the event and number of tickets
		this.event = event;
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return event.getTicketPrice() * quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity() {
		this.quantity = quantity;
	}
	public LiveEvent getEvent() {
		return event;
	}
	@Override
	public String toString() { //Ticket (x50) - £900 etc
		return event.getEventName() + "Ticket (x" + quantity + ") - £" + getTotalPrice();
	}
}

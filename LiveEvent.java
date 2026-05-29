
public abstract class LiveEvent {
	private int eventID;
	private LiveEventCategory liveEventCategory;
	private String eventName;
	private AgeRestrictionCategory restriction;
	private int quantityInStock;
	private double performanceFee;
	private double ticketPrice;
	
	public LiveEvent(int eventID,
			 String eventName,
			 AgeRestrictionCategory restriction,
			 int quantityInStock,
			 double performanceFee,
			 double ticketPrice,
			 LiveEventCategory liveEventCategory) {
		//Initialising all details
		this.eventID = eventID;
		this.liveEventCategory = liveEventCategory;
		this.eventName = eventName;
		this.restriction = restriction;
		this.quantityInStock = quantityInStock;
		this.ticketPrice = ticketPrice;
		this.performanceFee = performanceFee;
	}
	public int getEventID() {
		return eventID;
	}
	public String getEventName() {
		return eventName;
	}
	public AgeRestrictionCategory getAgeRestriction() {
		return restriction;
	}
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public void setQuantityInStock(int newQuantityInStock) {
		this.quantityInStock = newQuantityInStock;
	}
	public double getPerformanceFee() {
		return performanceFee;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public LiveEventCategory getEventCategory() {
		return liveEventCategory;
	}
	
	@Override
	public abstract String toString();
}


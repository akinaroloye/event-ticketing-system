
public class PerformanceEvents extends LiveEvent{
	private String PerformanceLanguage;
	private String eventType;
	
	public PerformanceEvents(String[] parts) {
		super(Integer.parseInt(parts[0].trim()),
				parts[3].trim(), parts[4].trim().equals("Adults") ? AgeRestrictionCategory.Adults : AgeRestrictionCategory.All,
				Integer.parseInt(parts[5].trim()), Double.parseDouble(parts[6].trim()), Double.parseDouble(parts[7].trim()),
				LiveEventCategory.PERFORMANCE
				);
		this.PerformanceLanguage = parts[8].trim();	
		this.eventType = parts[2].trim();
	}
	public PerformanceEvents(int eventID,
			 String eventName,
			 AgeRestrictionCategory restriction,
			 int quantityInStock,
			 double performanceFee,
			 double ticketPrice,
			 String PerformanceLanguage,
			 String eventType) {
		super(eventID,eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.PERFORMANCE);
		this.PerformanceLanguage = PerformanceLanguage;	
		this.eventType = eventType;
	}

	public String get_PerformanceLanguage() { //getter for performance language
		return PerformanceLanguage;
	}
	public String getEventType() { //Event type getter
		return eventType;
	}
	@Override
	public String toString() { //Nicer formatted toString
		return "Event Name: " + getEventName() + "Event ID: " + getEventID() + 
				"Age Restriction: " + getAgeRestriction() + "Quantity: " + getQuantityInStock() +
				"Performance Fee: " + getPerformanceFee() + "Ticket price: " + getTicketPrice() + 
				"Language: " + PerformanceLanguage;
	}
}

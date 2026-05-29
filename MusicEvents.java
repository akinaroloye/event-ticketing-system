
public class MusicEvents extends LiveEvent{
	private int NumOfPerformers;
	private String eventType;
	
	public MusicEvents(String[] parts) {//If not adults then its for all
		super(Integer.parseInt(parts[0].trim()),
				parts[3].trim(), parts[4].trim().equals("Adults") ? AgeRestrictionCategory.Adults : AgeRestrictionCategory.All, 
				Integer.parseInt(parts[5].trim()), 
				Double.parseDouble(parts[6].trim()), 
				Double.parseDouble(parts[7].trim()),
				LiveEventCategory.MUSIC
				);

		this.NumOfPerformers = Integer.parseInt(parts[8].trim());
		this.eventType = parts[2].trim(); //Cant get event type from live event so 
	}
	//Manually create objects:
	public MusicEvents(int eventID,
			 String eventName,
			 AgeRestrictionCategory restriction,
			 int quantityInStock,
			 double performanceFee,
			 double ticketPrice,
			 int NumOfPerformers, String eventType) {
		super(eventID,eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.MUSIC);
		this.NumOfPerformers = NumOfPerformers;
		this.eventType = eventType;
	}

	public int getNumOfPerformers() {
		return NumOfPerformers;
	}
	public String getEventType() {
		return eventType;
	}
	@Override
	public String toString() {
		return "Event Name: " + getEventName() + "Event ID: " + getEventID() + 
				"Age Restriction: " + getAgeRestriction() + "Quantity: " + getQuantityInStock() +
				"Performance Fee: " + getPerformanceFee() + "Ticket price: " + getTicketPrice() + 
				"Number of performers: " + NumOfPerformers;
	}
}

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class AdminPerms { //Essentially a menu for the admin users
	private static ArrayList<LiveEvent> eventList; //List that stores all events
	public static void main(String[] args) throws IOException {
		loadEvents(); //loads events from file
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(rd);
		while(true) { //loop will run until user signs out
			System.out.println("\n---ADMIN Options---");
			System.out.println("---1) View all events & their attributes---");
			System.out.println("---2) Add an event to the existing event list---");
			System.out.println("---3) Sign out!---");
			System.out.println("---------------");
			System.out.println("Pick an option via its number: ");
			String choice = br.readLine();
			if (choice.equals("1")) {
				viewEvents(); //Displays all events
			} else if (choice.equals("2")) {
				addEvent(br); //Add an event to stock.txt
			} else if (choice.equals("3")) {
				System.out.println("Successfully logged out.");
				return; //Ends loop
			} else {
				System.out.println("Invalid choice! Pick either 1, 2 or 3.");
			}
		}
	}
	public static boolean isUnique(int id) { //Cant have duplicate event IDs, this prevents that
		for (LiveEvent event : eventList) {
			if (event.getEventID() == id) {
				return false;
			}
		}
		return true;
	}
	public static void loadEvents() throws IOException{
		eventList = new ArrayList<>();
		FileReader inputFile = new FileReader("Stock.txt");
		BufferedReader br = new BufferedReader(inputFile);
		String line = null;
		//Read every line, one line = one event
		while((line = br.readLine()) != null) {
			String parts[] = line.split(",");
			String category = parts[1].trim();
			//Either create a performance event or a music event, based on whats stated in stock.txt
			if (category.strip().equalsIgnoreCase("Music")) {
				MusicEvents mEvent = new MusicEvents(parts);
				eventList.add(mEvent);
			} else if (category.equalsIgnoreCase("Performance")){
				PerformanceEvents pEvents = new PerformanceEvents(parts);
				eventList.add(pEvents);
			}else {
				System.out.println("Unexpected error!");
			}
		}
		br.close();
		
		Collections.sort(eventList, new Comparator<LiveEvent>() { //Code to sort events based on ticket price in increasing order
		@Override 
		public int compare(LiveEvent Event1, LiveEvent Event2) {
			return Double.compare(Event1.getTicketPrice(), Event2.getTicketPrice());
		}
		});
	}
	//Display all events
	public static void viewEvents() {
		if (!(eventList.isEmpty())) {
			System.out.println("All events");
			System.out.println("--------------");
			for (LiveEvent event : eventList) {
				String EventView = "";
				EventView += "\nEvent ID: " +event.getEventID()+"\nEventName: " + event.getEventName(); 
				if (event.getEventCategory() == LiveEventCategory.MUSIC) { //Ensures only details that are relevant to each category are shown
					EventView += "\nCategory: MUSIC \nType: " + ((MusicEvents) event).getEventType();
					EventView +="\nNumber of Performers: " +((MusicEvents) event).getNumOfPerformers();
				} else {
					EventView += "\nCategory: Performance \nType: " + ((PerformanceEvents) event).getEventType();
					EventView += "\nLanguage: " + ((PerformanceEvents) event).get_PerformanceLanguage();
				}

				EventView += "\nAge Restriction: " + event.getAgeRestriction() + "\nQuantity in Stock: " + event.getQuantityInStock() + "\nTicket Price: £" + event.getTicketPrice() + "\nPerformance Fee: £" + event.getPerformanceFee(); 
				System.out.println(EventView);
				System.out.println("--------------");
			}
			System.out.println("(In order of ticket price)");
		} else {
			System.out.println("No events right now, add events if necessary!");
		}
	}
	//Saves all current events in Stock.txt, including any new ones I may have added 
	public static void saveEvents() throws IOException{
		FileWriter outputFile = new FileWriter("Stock.txt");
		BufferedWriter bw = new BufferedWriter(outputFile);
		for (LiveEvent event : eventList) {
			String updatedStock = event.getEventID() +", ";
			if (event.getEventCategory() == LiveEventCategory.PERFORMANCE) {
				updatedStock += "Performance, ";
				updatedStock += ((PerformanceEvents) event).getEventType();
			} else {
				updatedStock += "Music, ";
				updatedStock += ((MusicEvents) event).getEventType();
			}
			updatedStock += ", " + event.getEventName() + ", " + event.getAgeRestriction() + ", " + event.getQuantityInStock() + ", " + event.getPerformanceFee() + ", " + event.getTicketPrice() + ", ";
			if (event.getEventCategory() == LiveEventCategory.PERFORMANCE) {
				updatedStock += ((PerformanceEvents) event).get_PerformanceLanguage();
			} else {
				updatedStock += ((MusicEvents) event).getNumOfPerformers();
			}
			bw.write(updatedStock); //Format of updatedStock based on the format in the text file, csv style
			bw.newLine();
		}
		bw.close();
	}
	//Adding new events to the stock
	public static void addEvent(BufferedReader br) throws IOException {
		int eventID = 1;
		while(eventID == 1) {
			System.out.println("Enter the numerical event ID: ");
			String input = br.readLine().trim();
			eventID = Integer.parseInt(input);
			if (!isUnique(eventID)) { // Ensures theres no duplicate event IDs, event ID is a unique identifier
				System.out.println("The eventID is already in use...");
				return;
			}
		}
		System.out.println("Enter the events name: ");
		String EventName = br.readLine().trim();
		
		String EventCategory = "";
		while (EventCategory.equals("")) {
			System.out.println("Enter the events category number Performance(1), Music(2): ");
			EventCategory = br.readLine().trim();
			
			if (!(EventCategory.equals("1") || EventCategory.equals("2"))) { //Users must enter either 1 or 2 to pick their event category
				System.out.println("ERROR! Must only enter 1 or 2!");
				EventCategory = "";
		}
		}
		String eventType = "";
		//Slightly messy coding, but users must manually enter the event type. Alternatively I couldve used a number system to pick the event type, however I am under time contraints
		while (!(eventType.equalsIgnoreCase("DJ Set")) && !(eventType.equalsIgnoreCase("Theatre")) && !(eventType.equalsIgnoreCase("Live Concert")) && !(eventType.equalsIgnoreCase("Stand-up Comedy")) && !(eventType.equalsIgnoreCase("Magic"))) {
			System.out.println("Enter the event type ('DJ Set', 'Theatre', 'Live Concert', 'Stand-up Comedy', or 'Magic'): ");
			eventType = br.readLine();
			if (!(eventType.equalsIgnoreCase("DJ Set")) && !(eventType.equalsIgnoreCase("Theatre")) && !(eventType.equalsIgnoreCase("Live Concert")) && !(eventType.equalsIgnoreCase("Stand-up Comedy")) && !(eventType.equalsIgnoreCase("Magic"))) {
				System.out.println("Invalid input, enter your choice exactly as shown.");
			}
		}
		//Prompt to decide the age restriction of the new event
		System.out.println("Pick an age restriction. (1) ALL ages (2) Adults");
		String pickAge = "";
		AgeRestrictionCategory ageRestriction = AgeRestrictionCategory.All;
		while (pickAge.equals("")) {
			pickAge = br.readLine().trim();
			if (pickAge.equals("2")) {
				ageRestriction = AgeRestrictionCategory.Adults;
			} else if (!pickAge.equals("1")) {
				System.out.println("ERROR! Invalid choice. Pick either 1 or 2");
				pickAge = "";
			}
		}
		//Prompt to enter quantity
		int quantity = 0;
		while (quantity == 0) {
			System.out.println("Enter the quantity of tickets: ");
			try {
				quantity = Integer.parseInt(br.readLine().trim());
				if (!(quantity > 0)) {
					System.out.println("ERROR! Quantity too low");
					quantity = 0;
				}
			} catch (Error a){ //Takes any error and responds by saying there was an error, users will be in a constant loop to enter ticket quantity
				System.out.println("Error detected, make sure you input a valid NUMBER above 0");
			}
		}
		//duplicate of quantity, but gets performance fee
		double PerformanceFee = 0;
		while (PerformanceFee == 0) {
			System.out.println("Enter the performance fee of the event: ");
			try {
				PerformanceFee = Double.parseDouble(br.readLine().trim());
				if (!(PerformanceFee > 0)) {
					System.out.println("ERROR! Fee too low must be greater than zero");
					PerformanceFee = 0;
				}
			} catch (Error a){
				System.out.println("Error detected, make sure you input a valid NUMBER above 0");
			}
		}
		//duplicate of quantity, but gets ticketPrice. 
		double ticketPrice = -1; //Must start at -1 so that free tickets can be set to price of 0
		while (ticketPrice == -1) {
			System.out.println("Enter the ticket price of the event: ");
			try {
				ticketPrice = Double.parseDouble(br.readLine().trim());
				if (!(ticketPrice >= 0)) {
					System.out.println("ERROR! Ticket price too low must be greater than zero or equal to zero.");
					ticketPrice = -1;
				}
			} catch (Error a){
				System.out.println("Error detected, make sure you input a valid NUMBER above 0 or equal to zero");
			}
		}
		//Gathering number of performers if its a music event
		if (EventCategory.equals("1")) {
			int NumPerformers = 0;
			while (NumPerformers == 0) {
				System.out.println("Enter the number of performers: ");
				try {
					NumPerformers = Integer.parseInt(br.readLine().trim());
					if (!(NumPerformers > 0)) {
						System.out.println("ERROR! Number of performers too low");
						NumPerformers = 0;
					}
				} catch (Error a){
					System.out.println("Error detected, make sure you input a valid NUMBER above 0");
				}
			}
			MusicEvents newEvent = new MusicEvents(eventID,EventName, ageRestriction, quantity, PerformanceFee, ticketPrice, NumPerformers, eventType);
			eventList.add(newEvent);
			FileWriter outputFile = new FileWriter("Stock.txt",true); //"true" stops it from deleting the whole stock.txt file upon each addition.
			BufferedWriter bw = new BufferedWriter(outputFile);
			bw.write(eventID+", Music, "+eventType+", "+EventName+", "+ageRestriction+", "+quantity+", "+PerformanceFee+", "+ticketPrice+", "+NumPerformers+"\n");
			bw.close();
			//Append the new event to the stock.txt file
		} else {
			String Language = ""; //if its a performance event it requires a language, a part of its additional info
			while (Language == "") {
				System.out.println("Enter the performance language: ");
				try {
					Language = br.readLine().trim();

				} catch (Error a){
					System.out.println("Error detected, make sure you input a valid NUMBER above 0");
				}
			}
			PerformanceEvents newEvent = new PerformanceEvents(eventID,EventName, ageRestriction, quantity, PerformanceFee, ticketPrice, Language, eventType);
			eventList.add(newEvent);
			FileWriter outputFile = new FileWriter("Stock.txt", true);
			BufferedWriter bw = new BufferedWriter(outputFile);
			bw.write(eventID+", Performance, "+eventType+", "+EventName+", "+ageRestriction+", "+quantity+", "+PerformanceFee+", "+ticketPrice+", "+Language +"\n");
			bw.close();
		}
		Collections.sort(eventList, new Comparator<LiveEvent>() { //order the events again with the new event
			@Override 
			public int compare(LiveEvent Event1, LiveEvent Event2) {
				return Double.compare(Event1.getTicketPrice(), Event2.getTicketPrice());
			}
			});
		System.out.println("Successfully added the event to the system!");
	}
}
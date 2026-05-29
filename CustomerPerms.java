import java.io.*;
import java.util.*;
public class CustomerPerms {
	private static ArrayList<LiveEvent> eventList;
	private static Address usersAddress;
	private static cart customersCart;
	
	public static void main(String[] args) throws IOException {
		loadEvents();
		customersCart = new cart();
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(rd);
		while(true) {
			System.out.println("\n---CUSTOMER Options---");
			System.out.println("---1) View all events & their attributes---");
			System.out.println("---2) Add a ticket to basket---");
			System.out.println("---3) View contents of basket---");
			System.out.println("---4) Pay for items in basket---");
			System.out.println("---5) Cancel basket---");
			System.out.println("---6) Search for item---");
			System.out.println("---------------");
			System.out.println("Pick an option via its number: ");
			String choice = br.readLine();
			if (choice.equals("1")) {
				viewEvents();
			} else if (choice.equals("2")) {
				addTicket(br);
			} else if (choice.equals("3")) {
				viewBasket();
			} else if (choice.equals("4")) {
				completePayment(br);
			} else if (choice.equals("5")) {
				cancelBasket();
			} else if (choice.equals("6")) {
				System.out.println("Pick which you'd like to search by:");
				System.out.println("A) Fast search/look-up for a given event ID");
				System.out.println("B) Search/Filter performances based on a given language.");
				System.out.println("-----------------------");
				System.out.println("Enter A or B: ");
				String choiceOfFilter = br.readLine();
				if (choiceOfFilter.equalsIgnoreCase("A")) {
					searchEventsViaID(br);
				} else if (choiceOfFilter.equalsIgnoreCase("B")) {
					searchEventsViaLanguage(br);
				}else {
					System.out.println("Invalid choice, must enter A or B");
				}
				
				
			} else {
				System.out.println("Invalid choice! Pick either 1, 2, 3, 4, 5 or 6.");
			}
		}
	}
	public static void loadEvents() throws IOException{
		eventList = new ArrayList<>();
		FileReader inputFile = new FileReader("Stock.txt");
		BufferedReader br = new BufferedReader(inputFile);
		String line = null;

		while((line = br.readLine()) != null) {
			String parts[] = line.split(",");
			String category = parts[1].trim();
			
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
		
		//Sort events by their ticket price in increasing order W3SCHOOLS example
		Collections.sort(eventList, new Comparator<LiveEvent>() {
		@Override 
		public int compare(LiveEvent Event1, LiveEvent Event2) {
			return Double.compare(Event1.getTicketPrice(), Event2.getTicketPrice());
		}
		});
	}
	
	public static void viewEvents() {
		if (!(eventList.isEmpty())) {
			System.out.println("All events");
			System.out.println("--------------");
			for (LiveEvent event : eventList) {
				String EventView = "";
				EventView += "\nEvent ID: " +event.getEventID()+"\nEventName: " + event.getEventName(); 
				if (event.getEventCategory() == LiveEventCategory.MUSIC) {
					EventView += "\nCategory: MUSIC \nType: " + ((MusicEvents) event).getEventType();
					EventView +="\nNumber of Performers: " +((MusicEvents) event).getNumOfPerformers();
				} else {
					EventView += "\nCategory: Performance \nType: " + ((PerformanceEvents) event).getEventType();
					EventView += "\nLanguage: " + ((PerformanceEvents) event).get_PerformanceLanguage();
				}

				EventView += "\nAge Restriction: " + event.getAgeRestriction() + "\nQuantity in Stock: " + event.getQuantityInStock() + "\nTicket Price: £" + event.getTicketPrice(); 
				System.out.println(EventView);
				System.out.println("--------------");
			}
			System.out.println("(In order of ticket price)");
		} else {
			System.out.println("No events right now, add events if necessary!");
		}
	}
	
	public static void addTicket(BufferedReader br) throws IOException {
		System.out.println("Enter the events ID: ");
		int eventID;
		try {
			eventID = Integer.parseInt(br.readLine().trim());
		} catch (Error a) {
			System.out.println("Invalid input, eventID must be a 5 digit number!");
			return;
		}
		boolean foundEvent = false;
		LiveEvent EventMatch = null;
		for (LiveEvent event : eventList) {
			if (event.getEventID() == eventID) {
				foundEvent = true;
				EventMatch = event;
			}
		}
		if (!foundEvent) {
			System.out.println("ERROR! No event with that ID was found!");
			return;
		}
		if (EventMatch.getQuantityInStock() > 0) {
			System.out.println("How many tickets do you need: ");
			int quantity = 0;
			try {
				quantity = Integer.parseInt(br.readLine());
				if (quantity <= 0) {
					System.out.println("Quantity must be greater than 0! ");
					return;
				}
				if (quantity > EventMatch.getQuantityInStock()) {
					System.out.println("You've exceeded the available number of tickets (" + EventMatch.getQuantityInStock() + ").");
					return;
				} 
				customersCart.addToCart(EventMatch, quantity);
				System.out.println("Successfully added to your shopping basket!");
			} catch (Error a) {
				System.out.println("Unexpected error! Ensure you enter a numerical value");
				return;
			}
		} else {
			System.out.println("No tickets in stock for this event, sorry!");
			return;
		}
	}
	
	public static void viewBasket() {
		System.out.println("-----Your basket-----");
		System.out.println("---------------------");
		ArrayList<cartItems> items = customersCart.getCart();
		if(!items.isEmpty()) {
			int cartSize = items.size();
			for (int i = 0; i < cartSize; i++) {
				cartItems item = items.get(i);
				LiveEvent event = item.getEvent();
				System.out.println(item.toString());
			}
			System.out.println("---------------------");
			System.out.println("Your total is: " + customersCart.getTotalPrice());
		} else {
			System.out.println("Cart is empty");
		}
	}
	
	public static void completePayment(BufferedReader br) throws IOException{
		Address userAddress = null;
		if (!customersCart.isEmpty()) {
			PaymentMethod paymentMethod = null;
			System.out.println("Your address details are needed");
			System.out.println("-----------------------------------");
			System.out.println("What is your house number: ");
			String houseNumber = br.readLine().trim();
			System.out.println("What is your city: ");
			String city = br.readLine().trim();
			System.out.println("What is your postcode: ");
			String postcode = br.readLine().trim();
			
			userAddress = new Address(houseNumber, city, postcode);
			System.out.println("-----------------------------------");
			System.out.println("Your payment method is needed:");
			System.out.println("1) PayPal");
			System.out.println("2) Credit Card");
			System.out.println("Pick either one based on their numbers: ");
			String paymentChoice = br.readLine().trim();
			if (paymentChoice.equals("1")) {
				System.out.println("Enter your paypal email: ");
				String email = br.readLine().trim();
				try {
					paymentMethod = new paypalPayment(email);
				} catch (Error a) {
					System.out.println("Errpr!");
					System.out.println(a.getMessage());
					return;
				}
			} else if (paymentChoice.equals("2")) {
				System.out.println("Enter your card number: ");
				String cardNumber = br.readLine().trim();
				System.out.println("Enter your security code: ");
				String cardSecNumber = br.readLine().trim();
				try {
					paymentMethod = new creditCardPayment(cardNumber,cardSecNumber);
				} catch (Error a) {
					System.out.println("Error!");
					System.out.println(a.getMessage());
					return;
				}
			} else {
				System.out.println("Invalid payment method, must pick 1 or 2!");
				return;
			}
			System.out.println("Payment Successful!");
			double totalCost = customersCart.getTotalPrice();
			Receipt receipt = paymentMethod.processPayment(totalCost, userAddress);
			System.out.println(receipt.toString());
			ArrayList<cartItems> items = customersCart.getCart();
			for (cartItems item : items) {
				LiveEvent event = item.getEvent();
				event.setQuantityInStock(event.getQuantityInStock() - item.getQuantity()); //Updating quantity available in Stock.txt
			}
			saveEvents();
			cancelBasket(); //Empty basket because it still held items in basket following purchase
		} else {
			System.out.println("Cart is empty! Add items to use this feature. ");
			return;
		}
	}
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
			bw.write(updatedStock);
			bw.newLine();
		}
		bw.close();
	}
	public static void cancelBasket() {
		if (!customersCart.isEmpty()) {
			customersCart.clearCart();
			System.out.println("Done!");
		} else {
			System.out.println("Cart is empty!");
		}
	}
	public static void searchEventsViaID(BufferedReader br) throws IOException {
		System.out.println("Whats the event ID: ");
		try {
			String FilterOutput = "";
			int eventID = Integer.parseInt(br.readLine());
			boolean foundEvent = false;
			
			for (LiveEvent event : eventList) {
				if (event.getEventID() == eventID) {
					System.out.println("Found, heres its details: ");
					FilterOutput += "\nEvent ID: " +event.getEventID()+"\nEventName: " + event.getEventName();  
					if (event.getEventCategory() == LiveEventCategory.MUSIC) {
						FilterOutput += "\nCategory: MUSIC \nType: " + ((MusicEvents) event).getEventType();
						FilterOutput +="\nNumber of Performers: " +((MusicEvents) event).getNumOfPerformers();
					} else {
						FilterOutput += "\nCategory: Performance \nType: " + ((PerformanceEvents) event).getEventType();
						FilterOutput += "\nLanguage: " + ((PerformanceEvents) event).get_PerformanceLanguage();
					}

					FilterOutput += "\nAge Restriction: " + event.getAgeRestriction() + "\nQuantity in Stock: " + event.getQuantityInStock() + "\nTicket Price: £" + event.getTicketPrice(); 
					System.out.println(FilterOutput);
					foundEvent = true;
				}
			}
			if (!foundEvent) {
				System.out.println("No event with that ID can be found!");
			}
		} catch (Error a) {
			System.out.println("Unexpected error! Make sure your event ID is a 5 digit number");
			return;
		}
	}
	public static void searchEventsViaLanguage(BufferedReader br) throws IOException {
		System.out.println("Whats the langauge: ");
		String language = br.readLine();
		String FilterOutput = "";
		try {
			boolean foundEvent = false;
			
			for (LiveEvent event : eventList) {
				if (event.getEventCategory() == LiveEventCategory.PERFORMANCE) {
					PerformanceEvents performEvent = (PerformanceEvents) event;
					if (performEvent.get_PerformanceLanguage().equalsIgnoreCase(language)) {
						foundEvent = true;
						System.out.println("Found a match, heres its details: ");
						FilterOutput += "\nEvent ID: " +event.getEventID()+"\nEventName: " + event.getEventName();  
						if (event.getEventCategory() == LiveEventCategory.MUSIC) {
							FilterOutput += "\nCategory: MUSIC \nType: " + ((MusicEvents) event).getEventType();
							FilterOutput +="\nNumber of Performers: " +((MusicEvents) event).getNumOfPerformers();
						} else {
							FilterOutput += "\nCategory: Performance \nType: " + ((PerformanceEvents) event).getEventType();
							FilterOutput += "\nLanguage: " + ((PerformanceEvents) event).get_PerformanceLanguage();
						}

						FilterOutput += "\nAge Restriction: " + event.getAgeRestriction() + "\nQuantity in Stock: " + event.getQuantityInStock() + "\nTicket Price: £" + event.getTicketPrice(); 
						System.out.println(FilterOutput);
					}
				}
			}
			if (!foundEvent) {
				System.out.println("No event with that ID can be found!");
			}
		} catch (Error a) {
			System.out.println("Unexpected error! Make sure your event ID is a 5 digit number");
			return;
		}
	}
	public static void setUserAddress(Address address) {
        usersAddress = address;
    }
}

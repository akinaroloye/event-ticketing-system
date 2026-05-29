import java.util.*;
public class cart {
	private ArrayList<cartItems> items; //Holds all items added to cart
	
	public cart() { //new carts should always start off empty
		items = new ArrayList<>();
	}
	
	public void addToCart(LiveEvent event, int quantity) {
		if (quantity > event.getQuantityInStock()) { //ensures you dont buy more tickets than availablle
			System.out.println("You're attempting to add more tickets than whats available! Only ("+event.getQuantityInStock()+") more available. "); 
		} else {
			items.add(new cartItems(event, quantity));
		}
	}
	public void clearCart() { //empty cart
		items.clear();
	}
	public double getTotalPrice() {
		double totalCost = 0;
		for (cartItems i : items) {
			totalCost += i.getTotalPrice();
		}
		return totalCost;
	}
	public ArrayList<cartItems> getCart() {
		return items;
	}
	public boolean isEmpty() { //to know if the carts empty
		return items.isEmpty();
	}
}

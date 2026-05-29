import java.time.LocalDate;
public class Receipt {
	private String receiptText;
	private LocalDate DateToday;
	
	public Receipt(double amount, String EmailOrCardNumber, String paymentMethod, Address fullAddress) {
		LocalDate DateToday = LocalDate.now();
		if (paymentMethod.equalsIgnoreCase("PayPal")) {// Send back receipts following successful purchases in format stated in specification
			receiptText = amount + " paid via PayPal using " + EmailOrCardNumber + " on " + DateToday + " and the billing address is " + fullAddress.get_fullAddress();
		} else if (paymentMethod.equalsIgnoreCase("Credit")) {
			receiptText = amount + " paid by Credit Card using " + EmailOrCardNumber + " on " + DateToday + ", and the billing address is " + fullAddress.get_fullAddress();
		}

	}
	public String toString() {
		return receiptText;
	}
}


public class paypalPayment implements PaymentMethod{
	private String Email;
	
	public paypalPayment(String Email) {
		if (!(Email== null) && Email.contains("@") && Email.contains(".")) { //Error handling, ensure email is valid, otherwise throw an error
			this.Email = Email.trim();
		} else {
			throw new IllegalArgumentException("ERROR! Invalid email, it must contain an @ sign and a full-stop.");
		}
	}
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		return new Receipt(amount, Email, "Paypal", fullAddress);
	}
}

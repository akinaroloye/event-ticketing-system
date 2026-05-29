public class creditCardPayment implements PaymentMethod {

	private String cardNumber;
	private String cardSecNumber;
	

	public creditCardPayment(String cardNumber,String cardSecNumber) {

		if (cardSecNumber.length() == 3 && cardNumber.length() == 6) { //make sure lengths are correct

			this.cardNumber = cardNumber;
			this.cardSecNumber = cardSecNumber;
		} else {

			throw new IllegalArgumentException("ERROR! Security code must be 3 digits, and card number must be 6 digits.");

		}

	}
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		//Send back a receipt for completed transactions
		return new Receipt(amount, cardNumber, "Credit", fullAddress);

	}

}



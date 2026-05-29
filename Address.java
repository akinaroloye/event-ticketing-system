
public class Address {
	//store address details
	public String houseNumber;
	public String City;
	public String Postcode;
	public String fullAddress;
	
	//constructor for initialising an address object
	public Address(String houseNumber, String City, String Postcode) {
		if (!(houseNumber == null && City == null && Postcode == null)) { //Validate required all inputs are there
			fullAddress = "House number: "+houseNumber.trim()+" | City: "+ City.trim()+" | Postcode: "+Postcode.trim();
			this.City = City.trim();
			this.houseNumber = houseNumber.trim();
			this.Postcode = Postcode.trim();
		} else { //Throw an exception if any fields are missing
			throw new IllegalArgumentException("ERROR! All options must be filled (Your house number, city and postcode).");
		}
	}
	public String get_fullAddress() { //return full correctly formatted address (human readable)
		return fullAddress;
	}
}

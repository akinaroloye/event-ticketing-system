
public class User {
	private String userID;
	private String username;
	private String name;
	private Address fullAddress;
	private String role;
	
	public User(String userID, String username, String name, Address fullAddress, String role) {
		this.userID = userID;
		this.username = username;
		this.name = name;
		this.fullAddress = fullAddress;
		this.role = role;
	}
	//All the getters for the user
	public String get_userID() {
		return userID;
	}
	public String get_username() {
		return username;
	}
	public String get_name() {
		return name;
	}
	public Address get_fullAddress() {
		return fullAddress;
	}
	public String get_role() {
		return role;
	}
}

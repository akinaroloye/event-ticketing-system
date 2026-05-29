import java.io.*;
import java.util.ArrayList;
public class Login {
	public static void main(String[] args) throws IOException {
		FileReader inputFile = new FileReader("UserAccounts.txt");
		//reading all users from the text file
		BufferedReader br = new BufferedReader(inputFile);
		String line = null;
		ArrayList<String> usersArray = new ArrayList<>();
		boolean userFound = false;
		System.out.println("User accounts: ");
		while((line = br.readLine()) != null) {
			String[] parts = line.split(",");
			System.out.println(parts[1].strip()); //Displaying all users in useraccounts, user should enter one correctly to login
			usersArray.add(parts[1].strip());
		}
		br.close();
		String[] usersArray2 = usersArray.toArray(new String[0]);
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader br2 = new BufferedReader(rd);
		while(true) {
			System.out.println("Enter your username: ");
			String username = br2.readLine();
			for(int i=0; i<usersArray2.length; i++) {
				if(usersArray2[i].equals(username)) { //Checking if the entered username exists in useraccounts.txt
					System.out.println("Welcome " + username);
					userFound = true;
					BufferedReader br3 = new BufferedReader(new FileReader("UserAccounts.txt"));
					String line3;
					while((line3 = br3.readLine()) != null) { //Finding users role to determine which menu they have access to, also store their role,city postcode and housenumber
						String[] parts = line3.split(",");
						if (parts[1].strip().equals(username)) {
							String role = parts[6].strip();
							String City = parts[5].strip();
							String Postcode = parts[4].strip();
							String houseNumber = parts[3].strip(); //Maybe use at checkout?
							Address userAddress = new Address(houseNumber, City, Postcode);
							System.out.println("Role: " + role);
							if (role.equalsIgnoreCase("Admin")) {
								AdminPerms.main(null);
							} else if (role.equalsIgnoreCase("Customer")) {
								CustomerPerms.setUserAddress(userAddress);
								CustomerPerms.main(null);
							}
						}
					}
					br3.close();
					break;
				}
			}
			if (!userFound) {
				System.out.println("User not found!"); //Loop continues if user isnt found
			} else {
				break;
			}	
		}
	}
}

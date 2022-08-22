import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions {
	
	//function below returns an ArrayList of type Product which consists of Keyboard and Mouse Objects
	public ArrayList<Product> getProducts(String fileName){
		ArrayList<Product> productList = new ArrayList<Product>();
		Scanner fileScanner = null;
		try {
			File inputFile = new File(fileName);
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				line.trim();
				String[] product = line.split(",");
				if(product[1].trim().equals("mouse")) {//find mice items
					Mouse m = new Mouse(product[0].trim(), product[1].trim(), product[2].trim(), product[3].trim(), product[4].trim(), product[5].trim(), Integer.parseInt(product[6].trim()), Double.parseDouble(product[7].trim()), Double.parseDouble(product[8].trim()), Integer.parseInt(product[9].trim()));
					productList.add(m);//add created Mouse object to ArrayList
				}else {
					Keyboard k = new Keyboard(product[0].trim(), product[1].trim(), product[2].trim(), product[3].trim(), product[4].trim(), product[5].trim(), Integer.parseInt(product[6].trim()), Double.parseDouble(product[7].trim()), Double.parseDouble(product[8].trim()), product[9].trim());
					productList.add(k);//add created Keyboard object to ArrayList
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return(productList);
	}
	
	
	
	public Admin getAdmin(String userID, String fileName){//function for returning an Admin object 
		Admin admin = null;
		Scanner fileScanner = null;
		try {
			File inputFile = new File(fileName);
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				line.trim();
				String[] user = line.split(",");
				if(user[1].trim().equals(userID) && user[6].trim().equals("admin")) {//get the correct admin that has logged in from UserAccounts.txt
					Address adr = new Address(user[3].trim(), user[4].trim(), user[5].trim());//create address object for Admin object constructor
					Admin adm = new Admin(user[0].trim(), user[1].trim(), user[2].trim(), adr, user[6].trim());
					admin = adm;
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return admin;
	}
	
	public Customer getCustomer(String userID, String fileName){//function for returning an Customer object 
		Customer customer = null;
		Scanner fileScanner = null;
		try {
			File inputFile = new File(fileName);
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				line.trim();
				String[] user = line.split(",");
				if(user[1].trim().equals(userID) && user[6].trim().equals("customer")) {//get the correct customer that has logged in from UserAccounts.txt
					Address adr = new Address(user[3].trim(), user[4].trim(), user[5].trim());//create address object for Customer object constructor
					Customer cust = new Customer(user[0].trim(), user[1].trim(), user[2].trim(), adr, user[6].trim());
					customer = cust;
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return customer;
	}
	
	public void writeProductsFile(String fileName, ArrayList<Product> productList){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fileName,false));//false here because we do not want to append information to the file
			for(Product p: productList) {
				if(p.getItem().equals("keyboard")) {
					Keyboard k = (Keyboard) p;//down casting product object to keyboard object to get the keyboard objects toString function
					bw.write(k.toStringFile() + "\n");
				}else {//product is of type mouse
					Mouse m = (Mouse) p;//down casting product object to mouse object to get the mouse objects toString function
					bw.write(m.toStringFile() + "\n");
				}
			}

		} catch(IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch(IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	//function which returns list of users so that all user ID's can be set to a jComboBox in the login frame
	public ArrayList<User> getUsers(String fileName){
		ArrayList<User> users = new ArrayList<>();
		Scanner fileScanner = null;
		try {
			File inputFile = new File(fileName);
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				line.trim();
				String[] user = line.split(",");
				if(user[6].trim().equals("customer")) {
					Address adr = new Address(user[3].trim(), user[4].trim(), user[5].trim());//create address object for Customer object constructor
					Customer cust = new Customer(user[0].trim(), user[1].trim(), user[2].trim(), adr, user[6].trim());
					users.add(cust);
				}else {
					Address adr = new Address(user[3].trim(), user[4].trim(), user[5].trim());//create address object for Admin object constructor
					Admin adm = new Admin(user[0].trim(), user[1].trim(), user[2].trim(), adr, user[6].trim());
					users.add(adm);
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if(fileScanner != null) {				
				fileScanner.close();
			}
		}
		return users;
	}
}
public abstract class User {
	private String userID;
	private String username;
	private String name;
	private Address adr;
	private String userType;
	
	public User(String userID, String username, String name, Address adr, String userType) {
		this.userID = userID;
		this.username = username;
		this.name = name;
		this.adr = adr;
		this.userType = userType;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}
	
	public Address getAdr() {
		return adr;
	}
	
	public String getUserType() {
		return userType;
	}
}
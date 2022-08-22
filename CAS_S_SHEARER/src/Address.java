public class Address {
	private String houseNumber;
	private String postCode;
	private String city;
	
	
	public Address(String houseNumber, String postCode, String city) {
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
	}
	
	@Override
	public String toString() {
		return houseNumber + " " + postCode + " " + city;
	}
}

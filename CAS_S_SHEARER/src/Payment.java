public class Payment{
	private Basket customerBasket;
	
	public Payment(Basket customerBasket){
		this.customerBasket = customerBasket;
	}
	
	public String getPaymentAmount() {
		return "" + customerBasket.getTotalCost() + ""; //Want to return String of amount so that it can be set to a Text box
	}
	
	public String toStringPaypal() {
		Customer customer = customerBasket.getCustomer();//get Customer object from Basket object
		Address adr = customer.getAdr();//get Address object from Customer object
		String message =  (customerBasket.getTotalCost()).toString() + " paid using PayPal, and the delivery address is " + adr.toString();
		return message;
	}
	
	public String toStringCreditCard() {
		Customer customer = customerBasket.getCustomer();//get Customer object from Basket object
		Address adr = customer.getAdr();//get Address object from Customer object
		String message = (customerBasket.getTotalCost()).toString() + " paid using Credit Card, and the delivery address is " + adr.toString();
		return message;
	}
	
}
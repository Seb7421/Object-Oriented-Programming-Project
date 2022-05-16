//Parent Class for Stock items
public abstract class Product {
	private String barcode;
	private String item;
	private String brand;
	private String colour;
	private String connectivity;
	private int quantity;
	private double originalCost;
	private double retailPrice;
	
	public Product(String barcode //Constructor
			, String item
			, String brand
			, String colour
			, String connectivity
			, int quantity
			, double originalCost
			, double retailPrice) {
		this.barcode = barcode;
		this.item = item;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantity = quantity;
		this.originalCost = originalCost;
		this.retailPrice = retailPrice;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public String getItem() {
		return item;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public String getColour() {
		return colour;
	}
	
	public String getConnectivity() {
		return connectivity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getOriginalCost() {
		return originalCost;
	}
	
	public double getRetailPrice() {
		return retailPrice;
	}
	
	public void reduceStockQuantity() {//setter method which reduces the quantity of available stock for a particular product by one
		int temp = this.quantity;
		this.quantity = temp - 1;
	}
	
	public void increaseStockQuantity() {//setter method which increases the quantity of available stock for a particular product by one
		int temp = this.quantity;		//this is used when the basket is cleared before payment and the items need to be added back into stock
		this.quantity = temp + 1;
	}
	
	public abstract String toStringFile();
	public abstract String toStringAdmin();
}

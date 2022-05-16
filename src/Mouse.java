public class Mouse extends Product{
	private String type;
	private int buttons;
	
	public Mouse(String barcode //Constructor
			, String item
			, String type
			, String brand
			, String colour
			, String connectivity
			, int quantity
			, double originalCost
			, double retailPrice
			, int buttons) {
		super(barcode,item,brand,colour,connectivity,quantity,originalCost,retailPrice);
		this.type = type;
		this.buttons = buttons;
	}
	
	public String getType() {
		return type;
	}
	
	public int getButtons() {
		return buttons;
	}

	public String toStringFile() {//used to write mouse information back to Stock.txt in the correct format
		return super.getBarcode() + ", " + "mouse" + ", " + this.getType() + ", " + super.getBrand() + ", " + super.getColour() + ", " 
				+ super.getConnectivity() + ", " + super.getQuantity() + ", " + super.getOriginalCost() + ", " + super.getRetailPrice() 
				+ ", " + this.getButtons();
	}
	
	@Override
	public String toStringAdmin() {//used to display informative message to admin once a product is added to stock
		return super.getQuantity() + " " + super.getConnectivity() + " " + this.getType() + " " + super.getColour() + " " + super.getBrand() +
				" mice with " + this.getButtons() + " buttons have been added to stock (wholesale: " + super.getOriginalCost() + 
				", retail: " + super.getRetailPrice() + ")";
	}
}

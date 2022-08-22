public class Keyboard extends Product{
	private String type;
	private String layout;
	
	public Keyboard(String barcode //Constructor
			, String item
			, String type
			, String brand
			, String colour
			, String connectivity
			, int quantity
			, double originalCost
			, double retailPrice
			, String layout) {
		super(barcode,item,brand,colour,connectivity,quantity,originalCost,retailPrice);
		this.type = type;
		this.layout = layout;
	}
	
	public String getLayout() {
		return layout;
	}
	
	public String getType() {
		return type;
	}
	
	public String toStringFile() {//used to write keyboard information back to Stock.txt in the correct format
		return super.getBarcode() + ", " + "keyboard" + ", " + this.getType() + ", " + super.getBrand() + ", " + super.getColour() + ", " 
				+ super.getConnectivity() + ", " + super.getQuantity() + ", " + super.getOriginalCost() + ", " + super.getRetailPrice() 
				+ ", " + this.getLayout();
	}
	
	@Override
	public String toStringAdmin() {//used to display informative message to admin once a product is added to stock
		return super.getQuantity() + " " + super.getConnectivity() + " " + this.getType() + " " + super.getColour() + " " + super.getBrand() +
				" keyboards with " + this.getLayout() + " layout have been added to stock (wholesale: " + super.getOriginalCost() + 
				", retail: " + super.getRetailPrice() + ")";
	}
}
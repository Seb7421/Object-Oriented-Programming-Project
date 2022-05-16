import java.util.ArrayList;

public class Customer extends User{

	public Customer(String userID, String username, String name, Address adr, String userType) {//constructor
		super(userID, username, name, adr, userType);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}
	
	public ArrayList<Product> getProductsFromSearch(String brand, String buttons) {
		ArrayList<Product> searchResults = new ArrayList<>();//ArrayList that will store products that match the search criteria
		
		ArrayList<Product> productList = null;//get full list of product objects
		try {
			Functions getData = new Functions();
			productList = getData.getProducts("Stock.txt");
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		if(buttons.equals("")) {//check if user did a search without choosing a button quantity(search just based off brand)
			for(Product p : productList) {
				if(p.getBrand().equals(brand)) {//compare all product brands in stock to the brand that was searched for
					searchResults.add(p);//add product to searchResults ArrayList
				}
			}
		}else if(brand.equals("")){//search just based off button quantity
			for(Product p : productList) {
				if(p.getItem().equals("mouse")) {//since Search includes button quantity, find mouse products in product list
					Mouse m = (Mouse) p;//cast product object to mouse object to access button quantity for object
					if(m.getButtons() == Integer.parseInt(buttons)) {
						searchResults.add(m);//mouse object fits search criteria
					}
				}
			}
		}else {//Search based off both brand and button quantity
			for(Product p : productList) {
				if(p.getItem().equals("mouse")) {//since Search includes button quantity, find mouse products in product list
					Mouse m = (Mouse) p;//cast product object to mouse object to access button quantity for object
					if(m.getBrand().equals(brand) && m.getButtons() == Integer.parseInt(buttons)) {
						searchResults.add(m);//mouse object fits search criteria
					}
				}
			}
		}
		
		return searchResults;
	}
	
	public boolean addProductBasket(String barcode) {
		boolean addProduct = false;
		
		ArrayList<Product> productList = null;//get full list of product objects
		try {
			Functions getData = new Functions();
			productList = getData.getProducts("Stock.txt");
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		
		for(Product p: productList){
			if(p.getBarcode().equals(barcode)) {
				if(p.getQuantity() >= 1) {//check if there is still stock left for product wanting to be added to Basket
					addProduct = true;
				}
			}
		}
		return addProduct;
		
		}
}
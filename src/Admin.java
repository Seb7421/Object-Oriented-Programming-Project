import java.util.ArrayList;

public class Admin extends User{

		public Admin(String userID, String username, String name, Address adr,String userType) {
			super(userID, username, name, adr, userType);
		}
		
		/*The two functions below returns a boolean which checks to see if a product object(product defined and wanted to be added 
		by admin) has a barcode that already exists in the product stock file, if so return false which will indicate not 
		to update the stock to include this item*/
		
		public boolean addProductMouse(ArrayList<Product> productList, Mouse newMouse) {
			boolean addMouse = true;
			for(Product p : productList) {//compare to all product barcodes
				if (p.getBarcode().equals(newMouse.getBarcode())){//check to see if the mouse being wanted to add already exists in stock or has a barcode used by another product
					addMouse = false;
				}
			}
			return addMouse;
		}
		
		public boolean addProductKeyboard(ArrayList<Product> productList, Keyboard newKeyboard) {
			boolean addKeyboard = true;
			for(Product p : productList) {//compare to all product barcodes
				if (p.getBarcode().equals(newKeyboard.getBarcode())){//check to see if the keyboard being wanted to add already exists in stock or has a barcode used by another product
					addKeyboard = false;
				}
			}
			return addKeyboard;
		}
		
		@Override
		public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
		}
}

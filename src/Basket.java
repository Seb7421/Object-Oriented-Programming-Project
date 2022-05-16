import java.util.ArrayList;

public class Basket {
	
private Customer customer;
private Double totalCost;
private ArrayList<Product> productsBasket;

public Basket(Customer customer, Double totalCost, ArrayList<Product> productsBasket) {
	this.customer = customer;
	this.totalCost = totalCost;
	this.productsBasket = productsBasket;
}

public ArrayList<Product> getProductsBasket() {
	return productsBasket;
}

public Double getTotalCost() {
	return totalCost;
}

public Customer getCustomer() {
	return customer;
}

public void setTotalZero() {//setter method for setting total to zero which is called when basket is emptied
	this.totalCost = 0.0;
}

public void setBasketToEmpty() {
	this.productsBasket.removeAll(productsBasket);//clears ArrayList of Basket Items
}

public void addProductBasket(Product basketProduct) {
	this.productsBasket.add(basketProduct);//add product to Baskets ArrayList
	double temp = this.totalCost;
	Double total = temp + basketProduct.getRetailPrice();//update Basket Total
	this.totalCost = Math.round(total*100.0)/100.0;//round total to two decimal places
	
	//after adding product to basket this products quantity must be updated in stock temporarily
	//to avoid customer from adding the same product to their basket too many times such that the quantity available for 
	//that product is exceeded 
	
	ArrayList<Product> productList = null;
	try {
		Functions getData = new Functions();
		productList = getData.getProducts("Stock.txt");//get Product objects
	}catch (Exception ex) {
		ex.printStackTrace();
		System.out.println(ex.getMessage());
	}
	
	for(Product p : productList) {
		if(p.getBarcode().equals(basketProduct.getBarcode())) {//find product in stock that the customer has just added to basket
			p.reduceStockQuantity();//reduce quantity of this item by one
		}
	}
	try {
		Functions getData = new Functions();
		getData.writeProductsFile("Stock.txt", productList);//write product objects to stock file to update quantity of item/s
	}catch (Exception ex) {
		ex.printStackTrace();
		System.out.println(ex.getMessage());
	}
}

//the function below is called when the customer clears the basket before any payment is made,
//because when a customer adds an item to the basket the stock for this item is updated in the Stock file by
//reducing the quantity by one, when the basket is cleared without paying for any items, the stock quantity must be updated
//again by including the basket Items back into the stock file

public void clearBasket() {
	
	ArrayList<Product> productList = null;//get product objects from Stock.txt
	try {
		Functions getData = new Functions();
		productList = getData.getProducts("Stock.txt");//get Product objects
	}catch (Exception ex) {
		ex.printStackTrace();
		System.out.println(ex.getMessage());
	}
	
	for(Product basketProduct : productsBasket) {
		for(Product stockProduct : productList) {
			if(stockProduct.getBarcode().equals(basketProduct.getBarcode())) {//find Item in Stock that is the same Item in the customer basket
				stockProduct.increaseStockQuantity();//increase quantity of this item by one
			}
		}
	}
	
	try {
		Functions getData = new Functions();
		getData.writeProductsFile("Stock.txt", productList);//write product objects to stock file to update quantity of item/s
	}catch (Exception ex) {
		ex.printStackTrace();
		System.out.println(ex.getMessage());
	}
	}
}
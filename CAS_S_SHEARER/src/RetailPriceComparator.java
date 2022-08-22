import java.util.Comparator;

public class RetailPriceComparator implements Comparator<Product>{
	//function which is used to sort products in ascending order by retail price
	public int compare(Product p1, Product p2) {
		if(p1.getRetailPrice() < p2.getRetailPrice()) {
			return -1;
		}else if(p1.getRetailPrice() > p2.getRetailPrice()) {
			return 1;
		}else {
			return 0;
		}
	}
}
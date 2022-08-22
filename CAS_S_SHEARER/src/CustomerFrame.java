import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField validationClearBasketTxt;
	private JTable customerViewTable;
	private JTable basketTable;
	private DefaultTableModel dtmBasketViewTable;
	private DefaultTableModel dtmCustomerViewTable;
	private JTextField TotalTextFieldBasket;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String userName = "user2";//default customer user ID
					CustomerFrame frame = new CustomerFrame(userName);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public CustomerFrame(String userName){
		
		Customer customerObject = null;
		ArrayList<Product> getProductsList = null;
		try {
			Functions getData = new Functions();
			customerObject = getData.getCustomer(userName,"UserAccounts.txt");//get Customer object
			getProductsList = getData.getProducts("Stock.txt");//get Product Objects
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		
		final ArrayList<Product> productList = getProductsList;//declare ArrayList of products Final so that this Array can be used throughout the code (this variable does not need to be changed)
		final Customer customer = customerObject;
		
		JOptionPane.showMessageDialog(null,"Welcome " + customer.getName(), "", JOptionPane.INFORMATION_MESSAGE);
		
		ArrayList<Product> productsBasket = new ArrayList<>();//create empty ArrayList of Product objects to be used as a parameter for Basket object
		Basket customerBasket = new Basket(customer,0.0,productsBasket);//create basket object for customer
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1049, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		tabbedPane.setBounds(0, 0, 1027, 552);
		contentPane.add(tabbedPane);
		
		JPanel productPanel = new JPanel();
		tabbedPane.addTab("Products", null, productPanel, null);
		productPanel.setLayout(null);
		
		JLabel brandLabel = new JLabel("Brand:");
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandLabel.setBounds(10, 15, 45, 18);
		productPanel.add(brandLabel);
		
		JComboBox<String> brandComboBox = new JComboBox<String>();
		brandComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ArrayList<String> brandList = new ArrayList<>();//list that will store the different brands of mice and keyboards
		for(Product p : productList) {
			brandList.add(p.getBrand());//get brands for all products and append to ArrayList
		}
		
		LinkedHashSet<String> removeDuplicateBrands = new LinkedHashSet<String>(brandList);//create LinkedHashSet of buttons which does not allow duplicates 
		ArrayList<String> brands = new ArrayList<>(removeDuplicateBrands);//create ArrayList from linkedHashSet so that there are no duplicates
		
		brandComboBox.addItem("");//add empty string first to jComboBox before brands, in case user does not want to select a brand option
		for(String b : brands) {
			brandComboBox.addItem(b);//add brand options to JComboBox
		}
		brandComboBox.setBounds(65, 11, 130, 26);
		productPanel.add(brandComboBox);
		
		JLabel mouseButtonsLabel = new JLabel("Mouse Buttons (optional):");
		mouseButtonsLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mouseButtonsLabel.setBounds(228, 12, 174, 29);
		productPanel.add(mouseButtonsLabel);
		
		JComboBox<String> buttonsComboBox = new JComboBox<String>();
		buttonsComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ArrayList<Integer> buttonList = new ArrayList<>();//Arraylist that will store the different button quantities for the mice
		for(Product p : productList) {
			if(p.getItem().equals("mouse")) {//want to find mice in products list to get button quantities
				Mouse m = (Mouse) p;//cast product object to mouse object
				buttonList.add(m.getButtons());//add button quantity of each mouse object to buttons array
			}
		}
		
		LinkedHashSet<Integer> removeDuplicateButtons = new LinkedHashSet<Integer>(buttonList);//create LinkedHashSet of buttons which does not allow duplicates 
		//buttons.clear();
		ArrayList<Integer> buttons = new ArrayList<>(removeDuplicateButtons);//create ArrayList from linkedHashSet so that there are no duplicates
		
		for(int i = 0; i < buttons.size() - 1; i++) {//using bubble sort, the array of the number of buttons for the mice is sorted in ascending order for jComboBox
			for(int j = buttons.size() - 1; j > i; j--) {
				if(buttons.get(j-1) > buttons.get(j)) {
					int temp = buttons.get(j-1);
					buttons.set(j-1, buttons.get(j));
					buttons.set(j,temp);
				}
			}
		}
		buttonsComboBox.addItem("");//add empty string first to jComboBox before button numbers, in case user does not want to select a button option
		for(Integer b : buttons) {
			buttonsComboBox.addItem(b.toString());//add number of button options to JComboBox
		}
		buttonsComboBox.setBounds(399, 15, 58, 22);
		productPanel.add(buttonsComboBox);
		
		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String brand = brandComboBox.getSelectedItem().toString();
				String buttons = buttonsComboBox.getSelectedItem().toString();
				
				if(brand.equals("") && buttons.equals("")) {//no option picked from either jComboBox's
					JOptionPane.showMessageDialog(null,"Please either select a brand and/or button quantity", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
				Customer customer = null;
				try {
					Functions getData = new Functions();
					customer = getData.getCustomer(userName,"UserAccounts.txt");//get Customer object
				}catch (Exception ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
				
				ArrayList<Product> results = customer.getProductsFromSearch(brand,buttons);//get Search results
				
				if(results.isEmpty()) {//check if search results ArrayList is empty
					JOptionPane.showMessageDialog(null,"Search obtained no results, please change brand selected or button quantity", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) customerViewTable.getModel();
					RetailPriceComparator retailComp = new RetailPriceComparator();
					Collections.sort(results,retailComp);
					
					model.setRowCount(0);//clears jTable before adding search data
					
					for(Product p : results) {
						if(p.getItem().equals("keyboard")) {
							Keyboard k = (Keyboard) p;//down casting product object to keyboard object to access getMehods
							String barcode = k.getBarcode();
							String item = k.getItem();
							String type = k.getType();
							String keyboardbrand = k.getBrand();
							String colour  = k.getColour();
							String connectivity = k.getConnectivity();
							int quantity = k.getQuantity();
							double retailCost = k.getRetailPrice();
							String layout = k.getLayout();
							
							Object[] data = {barcode,item,type,keyboardbrand,colour,connectivity,quantity,retailCost,layout, "-"};
							
							model.addRow(data);
							
						}else {//if product is not a keyboard then it is a mouse
							Mouse m = (Mouse) p;//down casting product object to mouse object to access getMehods
							String barcode = m.getBarcode();
							String item = m.getItem();
							String type = m.getType();
							String mousebrand = m.getBrand();
							String colour  = m.getColour();
							String connectivity = m.getConnectivity();
							int quantity = m.getQuantity();
							double retailCost = m.getRetailPrice();
							int mousebuttons = m.getButtons();
							
							Object[] data = {barcode,item,type,mousebrand,colour,connectivity,quantity,retailCost,"-",mousebuttons};
							
							model.addRow(data);
						}
					}
				}
			}
			}
		});
		searchButton.setForeground(Color.BLACK);
		searchButton.setBounds(10, 50, 240, 38);
		productPanel.add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 1002, 414);
		productPanel.add(scrollPane);
		
		customerViewTable = new JTable();
		scrollPane.setViewportView(customerViewTable);
		dtmCustomerViewTable = new DefaultTableModel();
		dtmCustomerViewTable.setColumnIdentifiers(new Object[]{"Barcode", "item", "type", "brand", "colour", "connectivity", "quantity", "cost", "layout","buttons"});
		customerViewTable.setModel(dtmCustomerViewTable);
		DefaultTableModel model = (DefaultTableModel) customerViewTable.getModel();
		
		RetailPriceComparator retailComp = new RetailPriceComparator();
		Collections.sort(productList,retailComp);
		
		model.setRowCount(0);//sets number of rows in jtable to zero so every time the button is pressed the data in the jtable is 
							//deleted before adding the data again
		
		for(Product p : productList) {
			if(p.getItem().equals("keyboard")) {
				Keyboard k = (Keyboard) p;//down casting product object to keyboard object to access getMehods
				String barcode = k.getBarcode();
				String item = k.getItem();
				String type = k.getType();
				String keyboardbrand = k.getBrand();
				String colour  = k.getColour();
				String connectivity = k.getConnectivity();
				int quantity = k.getQuantity();
				double retailCost = k.getRetailPrice();
				String layout = k.getLayout();
				
				Object[] data = {barcode,item,type,keyboardbrand,colour,connectivity,quantity,retailCost,layout, "-"};
				
				model.addRow(data);
				
			}else {//if product is not a keyboard then it is a mouse
				Mouse m = (Mouse) p;//down casting product object to mouse object to access getMehods
				String barcode = m.getBarcode();
				String item = m.getItem();
				String type = m.getType();
				String mousebrand = m.getBrand();
				String colour  = m.getColour();
				String connectivity = m.getConnectivity();
				int quantity = m.getQuantity();
				double retailCost = m.getRetailPrice();
				int mousebuttons = m.getButtons();
				
				Object[] data = {barcode,item,type,mousebrand,colour,connectivity,quantity,retailCost,"-",mousebuttons};
				
				model.addRow(data);
			}
		}
		
		
		JLabel lblNewLabel_1 = new JLabel("Select Item Barcode:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(606, 15, 130, 26);
		productPanel.add(lblNewLabel_1);
		
		JComboBox<String> barcodeComboBox = new JComboBox<String>();
		barcodeComboBox.setBounds(746, 15, 99, 22);
		productPanel.add(barcodeComboBox);
		ArrayList<String> barcodes = new ArrayList<>();//create ArrayList that will store all the product barcodes
		for(Product p : productList) {
			barcodes.add(p.getBarcode());//add product barcodes to ArrayList
		}
		
		barcodeComboBox.addItem("");//add empty string to ComboBox as first entry 
		for(String code : barcodes) {
			barcodeComboBox.addItem(code);//add each barcode String to ComboBox
		}
		
		JButton addToBasket = new JButton("Add to Basket");
		addToBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String barcode = barcodeComboBox.getSelectedItem().toString();
				
				if(barcode.equals("") == false) {//check if user selected a barcode
					
					if(customer.addProductBasket(barcode)){//check if Item is out of stock
						Product requestedProduct = null;//create product variable
						
						for(Product p : productList) {
							if(p.getBarcode().equals(barcode)) {//find product object that customer wants to add to basket
								requestedProduct = p;//assign this product to Object requestedProduct above
							}
						}
						customerBasket.addProductBasket(requestedProduct);//add product to Basket and update Stock.txt
					}else{
						JOptionPane.showMessageDialog(null,"Sorry that Product is now out of Stock", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					//set the Total text field in Basket frame to the sum of the retail prices of Items in basket
					TotalTextFieldBasket.setText((customerBasket.getTotalCost()).toString());
					
					if((customerBasket.getProductsBasket().isEmpty()) == false){
						DefaultTableModel basketModel = (DefaultTableModel) basketTable.getModel();
						basketModel.setRowCount(0);
						
						for(Product p : customerBasket.getProductsBasket()) {
							if(p.getItem().equals("keyboard")) {
								Keyboard k = (Keyboard) p;//down casting product object to keyboard object to access getMehods
								String keyboardBarcode = k.getBarcode();
								String item = k.getItem();
								String type = k.getType();
								String keyboardbrand = k.getBrand();
								String colour  = k.getColour();
								String connectivity = k.getConnectivity();
								double retailCost = k.getRetailPrice();
								String layout = k.getLayout();
								
								Object[] data = {keyboardBarcode,item,type,keyboardbrand,colour,connectivity,retailCost,layout, "-"};
								
								basketModel.addRow(data);
								
							}else {//if product is not a keyboard then it is a mouse
								Mouse m = (Mouse) p;//down casting product object to mouse object to access getMehods
								String mouseBarcode = m.getBarcode();
								String item = m.getItem();
								String type = m.getType();
								String mousebrand = m.getBrand();
								String colour  = m.getColour();
								String connectivity = m.getConnectivity();
								double retailCost = m.getRetailPrice();
								int mousebuttons = m.getButtons();
								
								Object[] data = {mouseBarcode,item,type,mousebrand,colour,connectivity,retailCost,"-",mousebuttons};
								
								basketModel.addRow(data);
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(null,"Please select a barcode", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addToBasket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addToBasket.setBounds(746, 53, 138, 33);
		productPanel.add(addToBasket);
		
		JButton cancelsearch = new JButton("cancel Search");
		cancelsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) customerViewTable.getModel();
				
				ArrayList<Product> productList = null;
				try {
					Functions getData = new Functions();
					productList = getData.getProducts("Stock.txt");
				}catch (Exception ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
				
				//using RetailPriceComparator object, the ArrayList of product objects are sorted in ascending order by retail price 
				RetailPriceComparator retailComp = new RetailPriceComparator();
				Collections.sort(productList,retailComp);
				
				model.setRowCount(0);//sets number of rows in jtable to zero so every time the button is pressed the data in the jtable is 
									//deleted before adding the data again
				
				for(Product p : productList) {
					if(p.getItem().equals("keyboard")) {
						Keyboard k = (Keyboard) p;//down casting product object to keyboard object to access getMehods
						String barcode = k.getBarcode();
						String item = k.getItem();
						String type = k.getType();
						String keyboardbrand = k.getBrand();
						String colour  = k.getColour();
						String connectivity = k.getConnectivity();
						int quantity = k.getQuantity();
						double retailCost = k.getRetailPrice();
						String layout = k.getLayout();
						
						Object[] data = {barcode,item,type,keyboardbrand,colour,connectivity,quantity,retailCost,layout, "-"};
						
						model.addRow(data);
						
					}else {//if product is not a keyboard then it is a mouse
						Mouse m = (Mouse) p;//down casting product object to mouse object to access getMehods
						String barcode = m.getBarcode();
						String item = m.getItem();
						String type = m.getType();
						String mousebrand = m.getBrand();
						String colour  = m.getColour();
						String connectivity = m.getConnectivity();
						int quantity = m.getQuantity();
						double retailCost = m.getRetailPrice();
						int mousebuttons = m.getButtons();
						
						Object[] data = {barcode,item,type,mousebrand,colour,connectivity,quantity,retailCost,"-",mousebuttons};
						
						model.addRow(data);
					}
				}
			}
		});
		cancelsearch.setBounds(260, 65, 117, 23);
		productPanel.add(cancelsearch);
		
		JPanel basketPanel = new JPanel();
		tabbedPane.addTab("Basket", null, basketPanel, null);
		basketPanel.setLayout(null);
		
		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((customerBasket.getProductsBasket().isEmpty()) == false) {//check is Basket is empty
					DefaultTableModel basketModel = (DefaultTableModel) basketTable.getModel();
					PaymentFrame payment = new PaymentFrame(customerBasket,basketModel,TotalTextFieldBasket);
					payment.setVisible(true);//if basket is not empty open payment frame 
				}else {
					JOptionPane.showMessageDialog(null,"No Items in Basket", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		checkoutButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		checkoutButton.setBounds(10, 426, 209, 33);
		basketPanel.add(checkoutButton);
		
		JLabel lblNewLabel = new JLabel("Clear Basket (enter yes):");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 486, 158, 23);
		basketPanel.add(lblNewLabel);
		
		validationClearBasketTxt = new JTextField();
		validationClearBasketTxt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		validationClearBasketTxt.setBounds(168, 486, 66, 23);
		basketPanel.add(validationClearBasketTxt);
		validationClearBasketTxt.setColumns(10);
		
		JButton clearBasketButton = new JButton("Clear");
		clearBasketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clearBasketValidation = validationClearBasketTxt.getText().trim();
				
				//check if user typed 'yes' into text box to clear basket, and if there are any products in basket to begin with
				if(clearBasketValidation.equals("yes") && (customerBasket.getProductsBasket().isEmpty()) == false) {
					DefaultTableModel basketModel = (DefaultTableModel) basketTable.getModel();
					basketModel.setRowCount(0);//delete all rows in Basket table
					
					customerBasket.setTotalZero();//set Basket Total to 0.0
					TotalTextFieldBasket.setText((customerBasket.getTotalCost()).toString());//update Basket total
				
					customerBasket.clearBasket();//add Basket Items back into stock
					customerBasket.setBasketToEmpty();//empty the Basket Objects ArrayList of Products 
				}
			}
		});
		clearBasketButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clearBasketButton.setBounds(244, 486, 79, 23);
		basketPanel.add(clearBasketButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 1002, 360);
		basketPanel.add(scrollPane_1);
		
		basketTable = new JTable();
		scrollPane_1.setViewportView(basketTable);
		dtmBasketViewTable = new DefaultTableModel();
		dtmBasketViewTable.setColumnIdentifiers(new Object[]{"Barcode", "item", "type", "brand", "colour", "connectivity", "cost", "layout"});
		basketTable.setModel(dtmBasketViewTable);
		
		JLabel TotalLabel = new JLabel("Basket Total:");
		TotalLabel.setBounds(10, 392, 79, 23);
		basketPanel.add(TotalLabel);
		
		TotalTextFieldBasket = new JTextField();
		TotalTextFieldBasket.setBounds(99, 392, 66, 23);
		basketPanel.add(TotalTextFieldBasket);
		TotalTextFieldBasket.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u00A3");
		lblNewLabel_2.setBounds(86, 394, 66, 19);
		basketPanel.add(lblNewLabel_2);
		
	}
	
	public static void main1(String[] args) {}
}

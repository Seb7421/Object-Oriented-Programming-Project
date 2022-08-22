import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtmAdminViewTable;
	private JTable adminViewTable;
	private JTextField mouseBarcode;
	private JTextField mouseBrand;
	private JTextField mouseColour;
	private JTextField mouseQuantity;
	private JTextField mouseOriginalPrice;
	private JTextField mouseRetailPrice;
	private JTextField mouseButtons;
	private JTextField keyboardBarcode;
	private JTextField keyboardBrand;
	private JTextField keyboardColour;
	private JTextField keyboardQuantity;
	private JTextField keyboardOriginalPrice;
	private JTextField keyboardRetailPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String userName = "user1";
					AdminFrame frame = new AdminFrame(userName);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminFrame(String userName){
		
		 Admin admin = null;//declare Admin object
		try {
			Functions getData = new Functions();
			admin = getData.getAdmin(userName,"UserAccounts.txt");//get Admin object
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		
		JOptionPane.showMessageDialog(null,"Welcome " + admin.getName(), "", JOptionPane.INFORMATION_MESSAGE);//welcome message
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1049, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 0, 1027, 565);
		contentPane.add(tabbedPane);
		
		JPanel viewStockPanel = new JPanel();
		tabbedPane.addTab("View Products", null, viewStockPanel, null);
		viewStockPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 27, 1002, 499);
		viewStockPanel.add(scrollPane);
		
		adminViewTable = new JTable();
		scrollPane.setViewportView(adminViewTable);
		dtmAdminViewTable = new DefaultTableModel();
		dtmAdminViewTable = new DefaultTableModel();
		dtmAdminViewTable.setColumnIdentifiers(new Object[]{"Barcode", "item", "type", "brand", "colour", "connectivity", "quantity", "original cost (£)","retail cost (£)", "layout", "Buttons"});
		adminViewTable.setModel(dtmAdminViewTable);
		
		JButton showStockbtn = new JButton("Show/Update stock");
		showStockbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) adminViewTable.getModel();
				
				ArrayList<Product> productList = null;
				try {
					Functions getData = new Functions();
					productList = getData.getProducts("Stock.txt");//get Product Objects
				}catch (Exception ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
				
				RetailPriceComparator retailComp = new RetailPriceComparator();
				Collections.sort(productList,retailComp);
				
				model.setRowCount(0);//sets number of rows in jtable to zero so every time the button is pressed the data in the jtable is 
									//deleted before adding the data again
				
				for(Product p : productList) {
					if(p.getItem().equals("keyboard")) {
						Keyboard k = (Keyboard) p;//casting product object to keyboard object to access getMehods
						String barcode = k.getBarcode();
						String item = k.getItem();
						String type = k.getType();
						String brand = k.getBrand();
						String colour  = k.getColour();
						String connectivity = k.getConnectivity();
						int quantity = k.getQuantity();
						double orginalCost = k.getOriginalCost();
						double retailCost = k.getRetailPrice();
						String layout = k.getLayout();
						
						Object[] data = {barcode,item,type,brand,colour,connectivity,quantity,orginalCost,retailCost,layout, "-"};
						
						model.addRow(data);
						
					}else {//if product is not a keyboard then it is a mouse
						Mouse m = (Mouse) p;//down casting product object to mouse object to access getMehods
						String barcode = m.getBarcode();
						String item = m.getItem();
						String type = m.getType();
						String brand = m.getBrand();
						String colour  = m.getColour();
						String connectivity = m.getConnectivity();
						int quantity = m.getQuantity();
						double orginalCost = m.getOriginalCost();
						double retailCost = m.getRetailPrice();
						int buttons = m.getButtons();
						
						Object[] data = {barcode,item,type,brand,colour,connectivity,quantity,orginalCost,retailCost,"-",buttons};
						
						model.addRow(data);
					}
				}
			}
		});
		showStockbtn.setBounds(10, 0, 151, 23);
		viewStockPanel.add(showStockbtn);
		
		JTabbedPane AddStockTab = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Add Stock", null, AddStockTab, null);
		
		JPanel AddKeyboardStock = new JPanel();
		AddKeyboardStock.setLayout(null);
		AddStockTab.addTab("Keyboard", null, AddKeyboardStock, null);
		
		JLabel descriptionlabel_2_1_1 = new JLabel("Complete all fields below:");
		descriptionlabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		descriptionlabel_2_1_1.setBounds(10, 11, 218, 28);
		AddKeyboardStock.add(descriptionlabel_2_1_1);
		
		keyboardBarcode = new JTextField();
		keyboardBarcode.setColumns(10);
		keyboardBarcode.setBounds(81, 50, 147, 28);
		AddKeyboardStock.add(keyboardBarcode);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Barcode:");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2_1_1.setBounds(10, 50, 91, 20);
		AddKeyboardStock.add(lblNewLabel_1_2_1_1);
		
		JLabel lblNewLabel_2_2_1_1 = new JLabel("type:");
		lblNewLabel_2_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_2_1_1.setBounds(10, 97, 66, 20);
		AddKeyboardStock.add(lblNewLabel_2_2_1_1);
		
		JLabel lblNewLabel_3_2_1_1 = new JLabel("Brand:");
		lblNewLabel_3_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_2_1_1.setBounds(10, 144, 48, 14);
		AddKeyboardStock.add(lblNewLabel_3_2_1_1);
		
		keyboardBrand = new JTextField();
		keyboardBrand.setColumns(10);
		keyboardBrand.setBounds(81, 139, 126, 28);
		AddKeyboardStock.add(keyboardBrand);
		
		JLabel lblNewLabel_4_2_1_1 = new JLabel("Colour:");
		lblNewLabel_4_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_2_1_1.setBounds(10, 190, 81, 20);
		AddKeyboardStock.add(lblNewLabel_4_2_1_1);
		
		keyboardColour = new JTextField();
		keyboardColour.setColumns(10);
		keyboardColour.setBounds(79, 188, 114, 28);
		AddKeyboardStock.add(keyboardColour);
		
		JLabel lblNewLabel_6_2_1_1 = new JLabel("Connectivity:");
		lblNewLabel_6_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6_2_1_1.setBounds(10, 227, 106, 31);
		AddKeyboardStock.add(lblNewLabel_6_2_1_1);
		
		JLabel lblNewLabel_8_2_1_1 = new JLabel("Quantity:");
		lblNewLabel_8_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8_2_1_1.setBounds(10, 281, 79, 20);
		AddKeyboardStock.add(lblNewLabel_8_2_1_1);
		
		keyboardQuantity = new JTextField();
		keyboardQuantity.setColumns(10);
		keyboardQuantity.setBounds(83, 279, 48, 28);
		AddKeyboardStock.add(keyboardQuantity);
		
		JLabel lblNewLabel_9_2_1_1 = new JLabel("(number of keyboards you want to add to stock)");
		lblNewLabel_9_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_9_2_1_1.setBounds(135, 281, 326, 20);
		AddKeyboardStock.add(lblNewLabel_9_2_1_1);
		
		JLabel lblNewLabel_10_2_1_1 = new JLabel("Original Price:");
		lblNewLabel_10_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_10_2_1_1.setBounds(10, 328, 106, 28);
		AddKeyboardStock.add(lblNewLabel_10_2_1_1);
		
		keyboardOriginalPrice = new JTextField();
		keyboardOriginalPrice.setColumns(10);
		keyboardOriginalPrice.setBounds(114, 330, 66, 28);
		AddKeyboardStock.add(keyboardOriginalPrice);
		
		JLabel lblNewLabel_11_2_1_1 = new JLabel("Retail Price:");
		lblNewLabel_11_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_11_2_1_1.setBounds(10, 381, 106, 26);
		AddKeyboardStock.add(lblNewLabel_11_2_1_1);
		
		keyboardRetailPrice = new JTextField();
		keyboardRetailPrice.setColumns(10);
		keyboardRetailPrice.setBounds(101, 382, 66, 28);
		AddKeyboardStock.add(keyboardRetailPrice);
		
		JLabel lblNewLabel_12_2_1_1 = new JLabel("Layout:");
		lblNewLabel_12_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_12_2_1_1.setBounds(10, 423, 71, 28);
		AddKeyboardStock.add(lblNewLabel_12_2_1_1);
		
		JComboBox keyboardType = new JComboBox();
		keyboardType.setBounds(81, 95, 126, 28);
		keyboardType.setModel(new DefaultComboBoxModel(KeyboardTypes.values()));//add values from enum type to ComboBox
		AddKeyboardStock.add(keyboardType);
		
		JComboBox keyboardConnectivity = new JComboBox();
		keyboardConnectivity.setBounds(114, 230, 126, 28);
		keyboardConnectivity.setModel(new DefaultComboBoxModel(ProductConnectivity.values()));//add values from enum type to ComboBox
		AddKeyboardStock.add(keyboardConnectivity);
		
		JComboBox keyboardLayout = new JComboBox();
		keyboardLayout.setBounds(81, 422, 55, 28);
		keyboardLayout.setModel(new DefaultComboBoxModel(KeyboardLayouts.values()));//add values from enum type to ComboBox
		AddKeyboardStock.add(keyboardLayout);
		
		JButton addKeyboardBtn = new JButton("Add Keyboard to Stock");
		addKeyboardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get all the values from the input fields
				String barcode = keyboardBarcode.getText().trim();
				String type = keyboardType.getSelectedItem().toString();//get value from ComboBox
				String brand = keyboardBrand.getText().trim();
				String colour = keyboardColour.getText().trim();
				String connectivity = keyboardConnectivity.getSelectedItem().toString();
				String quantity = keyboardQuantity.getText().trim();
				String originalPrice = keyboardOriginalPrice.getText().trim();
				String retailPrice = keyboardRetailPrice.getText().trim();
				String layout = keyboardLayout.getSelectedItem().toString();
				
				
				//validation to check if all input fields adhere to the correct formats
				if(barcode.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Barcode field", "Error", JOptionPane.ERROR_MESSAGE);//no input field can be left blank
				}else if(brand.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Brand field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(colour.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Colour field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(quantity.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Quantity field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(originalPrice.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Original Price field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(retailPrice.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Retail Price field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if((barcode.matches("[0-9]+")) == false || barcode.length() != 6){//check to see if the barcode only contains numbers and is the correct length
					JOptionPane.showMessageDialog(null, "Invalid details: Barcode must only include numbers, and must be of length 6", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(quantity.matches("[0-9]+") == false){
					JOptionPane.showMessageDialog(null, "Invalid details: Please make sure that the quantity is an integer", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(originalPrice.matches("[0-9.]+") == false || retailPrice.matches("[0-9.]+") == false ) {//check if prices entered are decimal numbers
					JOptionPane.showMessageDialog(null, "Make sure Original Price and Retail Price are positive decimal with a decimal point", "Error", JOptionPane.ERROR_MESSAGE);
				}else if((brand.matches("[a-zA-z]+") == false) || (colour.matches("[a-zA-z]+") == false)){//make sure text entered does not contain numbers
					JOptionPane.showMessageDialog(null, "Invalid details: Please make sure that the brand and colour you have entered only contain letters", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					/*get product list to add new product to it and write these
					products to the stock file if the new keyboard defined does not 
					already exist or is not using a mouses barcode*/
					ArrayList<Product> productList = null;
					Admin admin = null;
					try {
						Functions getData = new Functions();
						productList = getData.getProducts("Stock.txt");
						admin = getData.getAdmin(userName, "UserAccounts.txt");//create admin class to access function to check if keyboard can be added to stock
					}catch (Exception ex) {
						ex.printStackTrace();
						System.out.println(ex.getMessage());
					}
					final DecimalFormat priceFormat = new DecimalFormat("#.00",//set up a new DecimalFormat to round the entered prices to two decimal places incase the user enters more than two decimals	
							DecimalFormatSymbols.getInstance(Locale.UK));//change return string of decimal number which includes a comma to including a point
					String originalCost = priceFormat.format(Double.parseDouble(originalPrice));
					String retailCost = priceFormat.format(Double.parseDouble(retailPrice));
					
					Keyboard newKeyboard = new Keyboard(barcode,"keyboard",type,brand,colour,connectivity,Integer.parseInt(quantity),Double.parseDouble(originalCost),Double.parseDouble(retailCost),layout);
					if(admin.addProductKeyboard(productList, newKeyboard)) {//check if keyboard can be added to stock
						productList.add(newKeyboard);
						
						try {
							Functions getData = new Functions();
							getData.writeProductsFile("Stock.txt", productList);//write product objects to stock file to include new product
						}catch (Exception ex) {
							ex.printStackTrace();
							System.out.println(ex.getMessage());
						}
						//Success message, notifying the admin of the product that has just been added to stock
						JOptionPane.showMessageDialog(null,newKeyboard.toStringAdmin(),"Success", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Keyboard barcode entered is already in use", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		addKeyboardBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		addKeyboardBtn.setBounds(10, 457, 276, 41);
		AddKeyboardStock.add(addKeyboardBtn);
		
		JPanel AddMouseSTock = new JPanel();
		AddMouseSTock.setLayout(null);
		AddStockTab.addTab("Mouse", null, AddMouseSTock, null);
		
		JLabel descriptionlabel_2 = new JLabel("Complete all fields below:");
		descriptionlabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		descriptionlabel_2.setBounds(10, 11, 218, 28);
		AddMouseSTock.add(descriptionlabel_2);
		
		mouseBarcode = new JTextField();
		mouseBarcode.setColumns(10);
		mouseBarcode.setBounds(81, 50, 147, 28);
		AddMouseSTock.add(mouseBarcode);
		
		JLabel lblNewLabel_1_2 = new JLabel("Barcode:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 50, 91, 20);
		AddMouseSTock.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("type:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_2.setBounds(10, 97, 66, 20);
		AddMouseSTock.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3_2 = new JLabel("Brand:");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_2.setBounds(10, 144, 48, 14);
		AddMouseSTock.add(lblNewLabel_3_2);
		
		mouseBrand = new JTextField();
		mouseBrand.setColumns(10);
		mouseBrand.setBounds(81, 139, 126, 28);
		AddMouseSTock.add(mouseBrand);
		
		JLabel lblNewLabel_4_2 = new JLabel("Colour:");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_2.setBounds(10, 190, 81, 20);
		AddMouseSTock.add(lblNewLabel_4_2);
		
		mouseColour = new JTextField();
		mouseColour.setColumns(10);
		mouseColour.setBounds(79, 188, 114, 28);
		AddMouseSTock.add(mouseColour);
		
		JLabel lblNewLabel_6_2 = new JLabel("Connectivity:");
		lblNewLabel_6_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6_2.setBounds(10, 227, 106, 31);
		AddMouseSTock.add(lblNewLabel_6_2);
		
		JLabel lblNewLabel_8_2 = new JLabel("Quantity:");
		lblNewLabel_8_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8_2.setBounds(10, 281, 79, 20);
		AddMouseSTock.add(lblNewLabel_8_2);
		
		mouseQuantity = new JTextField();
		mouseQuantity.setColumns(10);
		mouseQuantity.setBounds(83, 279, 48, 28);
		AddMouseSTock.add(mouseQuantity);
		
		JLabel lblNewLabel_9_2 = new JLabel("(number of mice you want to add to stock)");
		lblNewLabel_9_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_9_2.setBounds(135, 281, 326, 20);
		AddMouseSTock.add(lblNewLabel_9_2);
		
		JLabel lblNewLabel_10_2 = new JLabel("Original Price:");
		lblNewLabel_10_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_10_2.setBounds(10, 328, 106, 28);
		AddMouseSTock.add(lblNewLabel_10_2);
		
		mouseOriginalPrice = new JTextField();
		mouseOriginalPrice.setColumns(10);
		mouseOriginalPrice.setBounds(114, 330, 66, 28);
		AddMouseSTock.add(mouseOriginalPrice);
		
		JLabel lblNewLabel_11_2 = new JLabel("Retail Price:");
		lblNewLabel_11_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_11_2.setBounds(10, 381, 106, 26);
		AddMouseSTock.add(lblNewLabel_11_2);
		
		mouseRetailPrice = new JTextField();
		mouseRetailPrice.setColumns(10);
		mouseRetailPrice.setBounds(101, 382, 66, 28);
		AddMouseSTock.add(mouseRetailPrice);
		
		JLabel lblNewLabel_12_2 = new JLabel("Number of Buttons:");
		lblNewLabel_12_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_12_2.setBounds(10, 423, 152, 28);
		AddMouseSTock.add(lblNewLabel_12_2);
		
		mouseButtons = new JTextField();
		mouseButtons.setColumns(10);
		mouseButtons.setBounds(159, 421, 48, 26);
		AddMouseSTock.add(mouseButtons);
		
		JComboBox mouseType = new JComboBox();
		mouseType.setBounds(81, 95, 126, 28);
		mouseType.setModel(new DefaultComboBoxModel(MouseTypes.values()));//add values from enum type to ComboBox
		AddMouseSTock.add(mouseType);
		
		JComboBox mouseConnectivity = new JComboBox();
		mouseConnectivity.setBounds(114, 230, 126, 28);
		mouseConnectivity.setModel(new DefaultComboBoxModel(ProductConnectivity.values()));
		AddMouseSTock.add(mouseConnectivity);
		
		JButton addMouseBtn = new JButton("Add Mouse to Stock");
		addMouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get all the values from the input fields
				String barcode = mouseBarcode.getText().trim();
				String type = mouseType.getSelectedItem().toString();//get value from ComboBox
				String brand = mouseBrand.getText().trim();
				String colour = mouseColour.getText().trim();
				String connectivity = mouseConnectivity.getSelectedItem().toString();
				String quantity = mouseQuantity.getText().trim();
				String originalPrice = mouseOriginalPrice.getText().trim();
				String retailPrice = mouseRetailPrice.getText().trim();
				String buttons = mouseButtons.getText().trim();
				
				//validation to check if all input fields adhere to the correct formats
				if(barcode.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Barcode field", "Error", JOptionPane.ERROR_MESSAGE);//no input field can be left blank
				}else if(brand.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Brand field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(colour.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Colour field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(quantity.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Quantity field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(originalPrice.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Original Price field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(retailPrice.equals("")) {
					JOptionPane.showMessageDialog(null, "Please complete the Retail Price field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(buttons.equals("")){
					JOptionPane.showMessageDialog(null, "Please complete the buttons Price field", "Error", JOptionPane.ERROR_MESSAGE);
				}else if((barcode.matches("[0-9]+")) == false || barcode.length() != 6){//check to see if the barcode only contains numbers and is the correct length
					JOptionPane.showMessageDialog(null, "Invalid details: Barcode must only include numbers, and must be of length 6", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(quantity.matches("[0-9]+") == false){
					JOptionPane.showMessageDialog(null, "Invalid details: Please make sure that the quantity is an integer", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(originalPrice.matches("[.0-9]+") == false || retailPrice.matches("[.0-9]+") == false) {//check if prices entered are decimal numbers
					JOptionPane.showMessageDialog(null, "Make sure Original Price and Retail Price are positive decimal numbers with a decimal point", "Error", JOptionPane.ERROR_MESSAGE);
				}else if((brand.matches("[a-zA-z]+") == false) || (colour.matches("[a-zA-z]+") == false)){//make sure text entered does not contain numbers
					JOptionPane.showMessageDialog(null, "Invalid details: Please make sure that the brand and colour you have entered only contain letters", "Error", JOptionPane.ERROR_MESSAGE);
				}else if (buttons.matches("[0-9]+") == false){
					JOptionPane.showMessageDialog(null, "Invalid details: Please make sure that the number of buttons enetered is a posistive integer", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					/*get product list to add new product to it and write these
					products to the stock file if the new mouse defined does not 
					already exist or is using a mouses barcode*/
					ArrayList<Product> productList = null;
					Admin admin = null;
					try {
						Functions getData = new Functions();
						productList = getData.getProducts("Stock.txt");
						admin = getData.getAdmin(userName, "UserAccounts.txt");//create admin class to access function to check if mouse can be added to stock
					}catch (Exception ex) {
						ex.printStackTrace();
						System.out.println(ex.getMessage());
					}
					final DecimalFormat priceFormat = new DecimalFormat("#.00",//set up a new DecimalFormat to round the entered prices to two decimal places incase the user enters more than two decimals	
							DecimalFormatSymbols.getInstance(Locale.UK));//change return string of decimal number which includes a comma to including a point
					String originalCost = priceFormat.format(Double.parseDouble(originalPrice));
					String retailCost = priceFormat.format(Double.parseDouble(retailPrice));
					
					Mouse newMouse = new Mouse(barcode,"mouse",type,brand,colour,connectivity,Integer.parseInt(quantity),Double.parseDouble(originalCost),Double.parseDouble(retailCost),Integer.parseInt(buttons));
					if(admin.addProductMouse(productList, newMouse)) {//check if mouse can be added to stock
						productList.add(newMouse);
						
						try {
							Functions getData = new Functions();
							getData.writeProductsFile("Stock.txt", productList);//write product objects to stock file to include new product
						}catch (Exception ex) {
							ex.printStackTrace();
							System.out.println(ex.getMessage());
						}
						//Success message, notifying the admin of the product that has just been added to stock
						JOptionPane.showMessageDialog(null,newMouse.toStringAdmin(),"Success", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Mouse barcode entered is already in use", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		addMouseBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		addMouseBtn.setBounds(10, 457, 276, 41);
		AddMouseSTock.add(addMouseBtn);
	}
}
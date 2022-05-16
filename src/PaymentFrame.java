import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PaymentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField totalTextField;
	private JTextField emailTextField;
	private JTextField cardNumberTextField;
	private JTextField securityCodeTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Basket customerBasket = null;
					DefaultTableModel basketModel = null;
					JTextField totalTextFieldBasket = null;
					PaymentFrame frame = new PaymentFrame(customerBasket,basketModel,totalTextFieldBasket);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param customerBasket 
	 * @param totalTextFieldBasket 
	 */
	public PaymentFrame(Basket customerBasket, DefaultTableModel basketModel, JTextField totalTextFieldBasket) {
		
		Payment payment = new Payment(customerBasket);//create new Payment object
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Payment");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(249, 11, 71, 25);
		contentPane.add(lblNewLabel);
		
		totalTextField = new JTextField();
		totalTextField.setBounds(63, 63, 58, 19);
		contentPane.add(totalTextField);
		totalTextField.setColumns(10);
		
		totalTextField.setText(payment.getPaymentAmount());//get Total to display in text box
		
		JLabel lblNewLabel_1 = new JLabel("PayPal");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(11, 99, 48, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Total:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(11, 65, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(100, 132, 173, 25);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("email address:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(11, 137, 79, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Credit Card");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(11, 193, 83, 25);
		contentPane.add(lblNewLabel_4);
		
		JButton confirmPaymentBtn = new JButton("Confirm Payment");
		confirmPaymentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = emailTextField.getText().trim();
				String cardNo = cardNumberTextField.getText().trim();
				String securityNo = securityCodeTextField.getText().trim();
				
				if((customerBasket.getProductsBasket().isEmpty()) == false) {/*prevent user from confirming payment a second time,
																			once payment is made the first time the Basket 
																		 	ArrayList of product objects is cleared, so check if this ArrayList is not empty*/
					
					if(email.equals("") && cardNo.equals("") && securityNo.equals("")){//Check if all payment fields are empty
						JOptionPane.showMessageDialog(null, "Please enter Payment Details for either Paypal or Credit Card", "Error", JOptionPane.ERROR_MESSAGE);
					}else if((email.length()>0 && cardNo.length()>0 && securityNo.length()>0) //check if information has been entered into both payment option fields
							|| (email.length()>0 && cardNo.length()>0 && securityNo.equals("")
						    || (email.length()>0 && cardNo.equals("") && securityNo.length()>0))) {
						JOptionPane.showMessageDialog(null, "Please enter Payment Details for only one payment option", "Error", JOptionPane.ERROR_MESSAGE);
					}else if(email.length()>0 && cardNo.equals("") && securityNo.equals("")) {
						String paymentMessage = payment.toStringPaypal();
						JOptionPane.showMessageDialog(null, paymentMessage, "Payment Approved", JOptionPane.INFORMATION_MESSAGE); //return Paypal message
						basketModel.setRowCount(0);//delete all rows in Basket table
						
						customerBasket.setTotalZero();//set Basket Total to 0.0
						totalTextFieldBasket.setText((customerBasket.getTotalCost()).toString());//update Basket total
						totalTextField.setText(payment.getPaymentAmount());//get Total to display in text box
						
						customerBasket.setBasketToEmpty();
						
					}else if((email.equals("") && cardNo.length()>0 && securityNo.equals(""))
							|| (email.equals("") && cardNo.equals("") && securityNo.length()>0)) { //check if both credit card fields have been completed
						JOptionPane.showMessageDialog(null, "Missing details for Credit Card", "Error", JOptionPane.ERROR_MESSAGE);						
					}else {
						if((cardNo.matches("[0-9]+")) && (securityNo.matches("[0-9]+") && email.equals(""))//Check if credit card details are integers and are the correct length
							&& (cardNo.length() == 6) && (securityNo.length() == 3)) {
							String paymentMessage = payment.toStringCreditCard();
							JOptionPane.showMessageDialog(null, paymentMessage, "Payment Approved", JOptionPane.INFORMATION_MESSAGE); //return Credit Card message
							basketModel.setRowCount(0);//delete all rows in Basket table
							
							customerBasket.setTotalZero();//set Basket Total to 0.0
							totalTextFieldBasket.setText((customerBasket.getTotalCost()).toString());//update Basket total
							totalTextField.setText(payment.getPaymentAmount());//get Total to display in text box
							
							customerBasket.setBasketToEmpty();
							
						}else {
							JOptionPane.showMessageDialog(null, "Invalid details for Credit Card: Make sure you have only entered numbers and that the required number of digits is met", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
			}else {
				JOptionPane.showMessageDialog(null, "Payment Complete, please close Payment frame", "Error", JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		confirmPaymentBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		confirmPaymentBtn.setBounds(11, 295, 172, 36);
		contentPane.add(confirmPaymentBtn);
		
		JLabel lblNewLabel_5 = new JLabel("Card Number ( 6-digit):");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(11, 229, 139, 19);
		contentPane.add(lblNewLabel_5);
		
		cardNumberTextField = new JTextField();
		cardNumberTextField.setBounds(149, 229, 98, 20);
		contentPane.add(cardNumberTextField);
		cardNumberTextField.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Security code (3-digit):");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(11, 259, 160, 25);
		contentPane.add(lblNewLabel_6);
		
		securityCodeTextField = new JTextField();
		securityCodeTextField.setBounds(149, 264, 48, 20);
		contentPane.add(securityCodeTextField);
		securityCodeTextField.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("\u00A3");
		lblNewLabel_7.setBounds(53, 66, 48, 14);
		contentPane.add(lblNewLabel_7);
	}
}
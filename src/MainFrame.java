import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		ArrayList<User> users = null;
		try {
			Functions getData = new Functions();
			users = getData.getUsers("UserAccounts.txt");//get list of user objects
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		
		final ArrayList<User> usersList = users;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel selectUserLbl = new JLabel("Please Select User:");
		selectUserLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectUserLbl.setEnabled(false);
		selectUserLbl.setBounds(10, 133, 104, 22);
		contentPane.add(selectUserLbl);
		
		JLabel headerLbl = new JLabel("Loughboroughs Computer Accesrories \r\nonline shop");
		headerLbl.setFont(new Font("Arial", Font.BOLD, 15));
		headerLbl.setBounds(31, 41, 418, 42);
		contentPane.add(headerLbl);
		
		JComboBox<String> username = new JComboBox<String>();
		username.setBounds(124, 134, 144, 22);
		contentPane.add(username);
		
		for(User u : usersList) {
			username.addItem(u.getUsername());//add all usernames from text file to jComboBox
		}
		
		JButton userSubmitBtn = new JButton("Submit");
		userSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String userName = username.getSelectedItem().toString().trim();//get selected username from JComboBox
				
				for(User u : usersList) {
					if(u.getUsername().equals(userName)) {//find user in User Object list with the username from the JcomboBox
						if(u.getUserType().equals("admin")) {
							AdminFrame adminfrm = new AdminFrame(userName);//if the user is a admin, open the admin frame
							adminfrm.setVisible(true); //open Admin Frame
						}else if(u.getUserType().equals("customer")) {//else if user is a customer, open customer frame
							CustomerFrame customer = new CustomerFrame(userName);
							customer.setVisible(true);
						}
					}
				}
			}
		});
		userSubmitBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userSubmitBtn.setBounds(297, 133, 91, 23);
		contentPane.add(userSubmitBtn);
		
		JLabel lblNewLabel = new JLabel("Welcome to");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(170, 26, 82, 14);
		contentPane.add(lblNewLabel);
	}
}
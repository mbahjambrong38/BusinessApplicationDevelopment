import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JInternalFrame implements ActionListener{
	boolean checkSignIn = false;
	String userId;
	
	JLabel labeljudul = new JLabel("Sign In to ....");
	JLabel labelEmail = new JLabel("Email");
	JLabel labelPassword = new JLabel("Password");
	JLabel labelBirthdate = new JLabel("Birhtdate");
	JLabel labelGender = new JLabel("Gender");
	JLabel labelAddress = new JLabel("Address");
	
	JTextField txtEmail = new JTextField();
	JPasswordField txtPassword = new JPasswordField();
	
	JButton buttonSignIn = new JButton("Sign In");
	
	JCheckBox rememberMe = new JCheckBox("Remember Me");
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(0, 2, 1, 4));
	JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	Connect c = new Connect();
	
	public void generateForm() {
		String query = "SELECT ur.UserEmail, ur.UserPassword FROM lastlogin ln, user ur WHERE ln.UserId = ur.UserId";
		c.rs = c.executeQuery(query);
		try {
			if (c.rs.next()) {
				txtEmail.setText(c.rs.getString(1));
				txtPassword.setText(c.rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labeljudul.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		panelNorth.add(labeljudul);
		panelCenter.add(labelEmail);
		panelCenter.add(txtEmail);
		panelCenter.add(labelPassword);
		panelCenter.add(txtPassword);
		panelCenter.add(rememberMe);
		
		panelSouth.add(buttonSignIn);
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
		
		buttonSignIn.addActionListener(this);
	}

	public Login() {
		
		this.setLayout(new BorderLayout(0,10));
		
		generateForm();
		
		this.setTitle("REGISTER FORM");
	
		this.setClosable(true);
		this.setSize(500, 200);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == buttonSignIn) {	
			
			String query = "SELECT UserId,UserEmail,UserPassword,UserRole FROM user WHERE UserEmail LIKE '"+ txtEmail.getText() +"' AND UserPassword LIKE '"+ txtPassword.getText() +"'";
			System.out.println(query);
			c.rs = c.executeQuery(query);
			String email = txtEmail.getText();
			String password = txtPassword.getText();
			String emailDb = "";
			String passwordDb = "";
			String roleDb = "";
			try {
				if (c.rs.next()) {
					userId = c.rs.getString(1);
					emailDb = c.rs.getString(2);
					passwordDb = c.rs.getString(3);
					roleDb = c.rs.getString(4);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if (email.length() == 0) 
				JOptionPane.showMessageDialog(null, "Email must be filled", "Warning", JOptionPane.WARNING_MESSAGE);
			else if (password.length() == 0) 
				JOptionPane.showMessageDialog(null, "Password must be filled", "Warning", JOptionPane.WARNING_MESSAGE);
			else {
				if (email.equals(emailDb) && password.toString().equals(passwordDb)) {
					this.setVisible(false);
					if(rememberMe.isSelected()) {
						checkSignIn = true;
						JOptionPane.showMessageDialog(null, "Hello, " + email.substring(0, email.indexOf('@')) + " You don't have to fill the password again next time.");	
						c.addRememberMe(userId);
						this.dispose();
					}else {		
						checkSignIn = true;
						JOptionPane.showMessageDialog(null, "Hello, " + email.substring(0, email.indexOf('@')));	
						c.removeRememberMe();
						this.dispose();
					}
				
					if(roleDb.equals("user")){
						User user = new User();
						user.setId(userId);
					}else if(roleDb.equals("admin")){
						Admin admin = new Admin();
					}
														
				}else {
					JOptionPane.showMessageDialog(null, "Email atau password anda salah!!" , "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		}
	}
}
			
			
	
		



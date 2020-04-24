import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Main extends JFrame implements ActionListener{
	
	JMenuBar menuBar = new JMenuBar();
	JMenu userMenu = new JMenu("User");
	JMenuItem signInItem = new JMenuItem("Sign in");
	JMenuItem signUpItem = new JMenuItem("Sign up");
	
	JDesktopPane content = new JDesktopPane();
	
	Register signUp;
	Login signIn;
	
	private boolean checkOpenMenu = false; 
	
	public void generateMenu() {
		
		menuBar.add(userMenu);
		userMenu.add(signInItem);
		userMenu.add(signUpItem);
		this.setJMenuBar(menuBar);
		signInItem.addActionListener(this);
		signUpItem.addActionListener(this);
		
	}
	
	public Main() {
		
		generateMenu();
		
		this.setContentPane(content);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signUpItem) {
			try {
				if (signIn.isClosed()) 
					checkOpenMenu = false;
				 else 
					checkOpenMenu = true;		
			}catch (Exception e2) { 
				checkOpenMenu = false;
			}finally {
				if (((signUp == null || signUp.isClosed())) && checkOpenMenu == false) {
					signUp = new Register();
					content.add(signUp);
				} 
			}
		
		} else if (e.getSource() == signInItem){
			try {
				if (signUp.isClosed()) 
					checkOpenMenu = false;
				else 
					checkOpenMenu = true;
				
			} catch (Exception e2) {
				checkOpenMenu = false;
			} finally {
				if (((signIn == null || signIn.isClosed())) && checkOpenMenu == false) {
					signIn = new Login();
					content.add(signIn);
				} 
			}	
		}
	}
}
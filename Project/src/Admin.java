import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.MenuSelectionManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Admin extends User implements ActionListener, MenuListener{
	JMenuBar menuBar = new JMenuBar();
	JMenu userMenu = new JMenu("User");
	JMenu productsMenu = new JMenu("Manage Products");
	JMenu transactionMenu = new JMenu("View Transactions History");
	JMenuItem signInItem = new JMenuItem("Sign In");
	JMenuItem signUpItem = new JMenuItem("Sign Up");
	JMenuItem signOutItem = new JMenuItem("Sign Out"); 
	JMenuItem updateProfileItem = new JMenuItem("Update Profile");
	JMenuItem exitItem = new JMenuItem("Exit"); 
	
	JDesktopPane content = new JDesktopPane();
	
	private boolean checkOpenMenu = false; 
	
	public void generateMenu() {
		
		userMenu.add(signInItem);
		userMenu.add(signUpItem);
		userMenu.add(signOutItem);
		userMenu.add(updateProfileItem);
		userMenu.add(exitItem);
		menuBar.add(userMenu);
		menuBar.add(productsMenu);
		menuBar.add(transactionMenu);
		
		this.setJMenuBar(menuBar);
		
		signInItem.setEnabled(false);
		signUpItem.setEnabled(false);
		updateProfileItem.setEnabled(false);
		
		signOutItem.addActionListener(this);
		updateProfileItem.addActionListener(this);
		exitItem.addActionListener(this);
		productsMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				ProductsForAdmin product = new ProductsForAdmin();
				content.add(product);		
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {			
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				
				
			}
		});
		transactionMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				HistoryAdmin history = new HistoryAdmin();
				content.add(history);			
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				
				
			}
		});
		
	}

	public Admin() {
		this.setContentPane(content);
		
		generateMenu();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signOutItem) {
			int result = JOptionPane.showConfirmDialog(null, "You are going to Sign Out \nAre you sure?", "Warning", JOptionPane.WARNING_MESSAGE , JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		}else if (e.getSource() == exitItem) {
			System.exit(0);
		}
		
	}
}

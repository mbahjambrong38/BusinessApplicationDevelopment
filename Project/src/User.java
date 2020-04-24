import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class User extends JFrame implements ActionListener, MenuListener{
	String userId;
	
	JMenuBar menuBar = new JMenuBar();
	JMenu userMenu = new JMenu("User");
	JMenu productsMenu = new JMenu("View Products");
	JMenu transactionMenu = new JMenu("Transactions");
	JMenuItem signInItem = new JMenuItem("Sign In");
	JMenuItem signUpItem = new JMenuItem("Sign Up");
	JMenuItem signOutItem = new JMenuItem("Sign Out"); 
	JMenuItem updateProfileItem = new JMenuItem("Update Profile");
	JMenuItem exitItem = new JMenuItem("Exit"); 
	JMenuItem myCartItem = new JMenuItem("My Cart");
	JMenuItem purchasedProductItem = new JMenuItem("Purhcased Products");
	
	JDesktopPane content = new JDesktopPane();
	
	UpdateProfile update;
	Cart cart;
	OnGoingUser onGoing;
	HistoryUser history;
	ProductsForUser product;
	
	private boolean checkOpenMenu = false;
	
	public void setMenu() {
		
		userMenu.add(signInItem);
		userMenu.add(signUpItem);
		userMenu.add(signOutItem);
		userMenu.add(updateProfileItem);
		userMenu.add(exitItem);
		transactionMenu.add(myCartItem);
		transactionMenu.add(purchasedProductItem);
		menuBar.add(userMenu);
		menuBar.add(productsMenu);
		menuBar.add(transactionMenu);
		
		this.setJMenuBar(menuBar);
		
		signInItem.setEnabled(false);
		signUpItem.setEnabled(false);
		
		signOutItem.addActionListener(this);
		updateProfileItem.addActionListener(this);
		exitItem.addActionListener(this);
		productsMenu.addMenuListener(this);
		myCartItem.addActionListener(this);
		purchasedProductItem.addActionListener(this);
		
	}
	
	public void setId(String userId) {
		this.userId = userId;
	}

	public User() {

		this.setContentPane(content);
		
		setMenu();
		
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
		}else if (e.getSource() == updateProfileItem) {
			try {
				if (update.isClosed()) 
					checkOpenMenu = false;
				else
					checkOpenMenu = true;
			} catch (Exception e2) {
				checkOpenMenu = false;
			} finally {
				if (((update == null || update.isClosed())) && checkOpenMenu == false) {
					update = new UpdateProfile();
					update.setId(userId);
					content.add(update);
				} 
			}
		}else if (e.getSource() == exitItem)
			System.exit(0);
		else if (e.getSource() == myCartItem) {
			try {
				if (cart.isClosed()) 
					checkOpenMenu = false;
				else
					checkOpenMenu = true;
			} catch (Exception e2) {
				checkOpenMenu = false;
			} finally {
				if (((cart == null || cart.isClosed())) && checkOpenMenu == false) {
					cart = new Cart();
					cart.setId(userId);
					content.add(cart);	
				} 
			}	
		}else if (e.getSource() == purchasedProductItem) {
			String[] options = {"On Going Transaction(s)" , "Finished Transaction(s)"};
			int result = JOptionPane.showOptionDialog(null, "Pick the data you'd like to view: ", "Data to be shown", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (result == 0) {
				try {
					if (onGoing.isClosed()) 
						checkOpenMenu = false;
					else
						checkOpenMenu = true;
				} catch (Exception e2) {
					checkOpenMenu = false;
				} finally {
					if (((onGoing == null || onGoing.isClosed())) && checkOpenMenu == false) {
						onGoing = new OnGoingUser();
						onGoing.setId(userId);
						content.add(onGoing);	
					} 
				}	
			}else if (result == 1) {
				try {
					if (history.isClosed()) 
						checkOpenMenu = false;
					else
						checkOpenMenu = true;
				} catch (Exception e2) {
					checkOpenMenu = false;
				} finally {
					if (((history == null || history.isClosed())) && checkOpenMenu == false) {
						history = new HistoryUser();
						history.setId(userId);
						content.add(history);	
					} 
				}	
			}
			
		}
		
	}


	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void menuSelected(MenuEvent e) {
		try {
			if (product.isClosed()) 
				checkOpenMenu = false;
			else
				checkOpenMenu = true;
		} catch (Exception e2) {
			checkOpenMenu = false;
		} finally {
			if (((product == null || product.isClosed())) && checkOpenMenu == false) {
				product = new ProductsForUser();
				product.setId(userId);
				content.add(product);	
			} 
		}	
		
	}

}

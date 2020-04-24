import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;


public class Cart extends JInternalFrame implements ActionListener, MouseListener{
	Integer totalPrice = 0;
	String userId;
	
	Vector<Vector<Object>> tableContent;
	Vector<Object> tableRow, tableColumn;
	
	DefaultTableModel dtm;
	
	JTable table;
	
	JScrollPane scrollPane;
	
	SpinnerModel value = new SpinnerNumberModel(0, 0, 100, 1);
	
	JLabel labelJudul;
	JLabel labelName;
	JLabel labelQty;
	JLabel labelColor;
	JLabel labelTotal;
	JLabel labelPrice;
	
	
	JTextField txtName;
	JTextField txtColor;
	
	JSpinner spinQty = new JSpinner(value);
	
	JButton buttonEdit, buttonUpdate, buttonCancel, buttonCheckOut;
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(3, 1, 0, 20));
	JPanel panelField = new JPanel(new GridLayout(0, 2, 5, 20));
	JPanel panelButton = new JPanel(new GridLayout(2, 3, 10, 10));
	JPanel panelSouth = new JPanel(new GridLayout(1, 2));
	JPanel panelSouthLeft = new JPanel(new GridLayout(1,2));
	JPanel panelSouthRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelPrice = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	Connect c = new Connect();
	
	public void addData(String name, String color, int qty) {
		tableRow = new Vector<>();		
		tableRow.add(name);
		tableRow.add(color);		
		tableRow.add(qty);
		tableContent.add(tableRow);
	}
	
	public void viewData() {
		tableContent = new Vector<>();
		tableColumn = new Vector<>();
		
		tableColumn.add("Product Name");
		tableColumn.add("Product Color");
		tableColumn.add("Product Qty");
		System.out.println(userId);
		
		String query = "SELECT products.ProductName,products.ProductColor,cart.ProductQty FROM cart,products WHERE cart.ProductId = products.ProductId AND cart.UserId LIKE '" + userId + "'";
		c.rs = c.executeQuery(query);
		
		try {
			while (c.rs.next()) {
				addData(c.rs.getString(1), c.rs.getString(2), c.rs.getInt(3));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		dtm = new DefaultTableModel(tableContent, tableColumn);
		table.setModel(dtm);
		
		
		dtm.fireTableDataChanged();
	}
	
	public void calculatePrice() {
		Integer result = 0;
		String query = "SELECT products.ProductPrice,cart.ProductQty FROM products,cart WHERE products.ProductId = cart.ProductId AND cart.UserId='" + userId + "'";
		c.rs = c.executeQuery(query);
		try {
			while (c.rs.next()) {
				result += (c.rs.getInt(1) * c.rs.getInt(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		totalPrice = result;
		labelPrice.setText(result.toString());
	}
	
	public void initComponent() {
		table= new JTable() {@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}};
		viewData();
		
		table.setAutoCreateRowSorter(true);
		
		scrollPane = new JScrollPane(table);
		
		table.addMouseListener(this);
		
		txtName = new JTextField();
		txtColor = new JTextField();
		txtName.setEditable(false);;
		txtColor.setEditable(false);
		spinQty.setEnabled(false);
		
		buttonCancel = new JButton("Cancel");
		buttonEdit = new JButton("Edit");
		buttonUpdate = new JButton("Update");
		buttonCheckOut = new JButton("Check Out");
		buttonUpdate.setEnabled(false);
		buttonCancel.setEnabled(false);
			
		labelJudul = new JLabel("Available Products: ");
		labelName = new JLabel("Name");
		labelQty = new JLabel("Quantity");
		labelColor = new JLabel("Color");
		labelTotal = new JLabel("TOTAL: ");
		labelPrice = new JLabel("");
		
		calculatePrice();
		
		panelButton.setSize(50, 20);
		
		table.addMouseListener(this);
		buttonEdit.addActionListener(this);
		buttonUpdate.addActionListener(this);
		buttonCancel.addActionListener(this);
		buttonCheckOut.addActionListener(this);
		
		
	}

	public void setComponent() {
		panelNorth.add(labelJudul);
		
		panelField.add(labelName);
		panelField.add(txtName);
		panelField.add(labelQty);
		panelField.add(spinQty);
		panelField.add(labelColor);
		panelField.add(txtColor);
		
		panelButton.add(buttonEdit);
		panelButton.add(buttonUpdate);
		panelButton.add(buttonCancel);
		panelButton.add(new JLabel());
		panelButton.add(new JLabel());
		panelButton.add(new JLabel());
		
		panelCenter.add(scrollPane);
		panelCenter.add(panelField);
		panelCenter.add(panelButton);
		
		panelTotal.add(labelTotal);
		panelPrice.add(labelPrice);
		panelSouthLeft.add(panelTotal);
		panelSouthLeft.add(panelPrice);
		panelSouthRight.add(buttonCheckOut);
		
		panelSouth.add(panelSouthLeft);
		panelSouth.add(panelSouthRight);
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
	}
	
	public void setId(String userId) {
		this.userId = userId;
		initComponent();
		setComponent();
	}
	
	public Cart() {
		
		this.setTitle("Nama JUDULNYA");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(900, 600);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonEdit) {
			if (txtName.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Choose 1 from table!", "Warning", JOptionPane.WARNING_MESSAGE);
			}else {
				buttonCancel.setEnabled(true);
				buttonUpdate.setEnabled(true);
				spinQty.setEnabled(true);
				buttonEdit.setEnabled(false);
				
				String name = txtName.getText();
				String color = txtColor.getText();

			}
		}else if (e.getSource() == buttonUpdate) {
			String name = txtName.getText();
			String color = txtColor.getText();
			int qty = (Integer)spinQty.getValue();
			String query = "SELECT ProductQty FROM products WHERE ProductName = '" + name + "' AND ProductColor = '" + color + "'"; 
			c.rs = c.executeQuery(query);
			try {
				if (c.rs.next()) {
					if (qty == 0) {
						c.deleteCart(name, color);
						viewData();
						calculatePrice();
					}else if(qty > c.rs.getInt(1)){
						JOptionPane.showMessageDialog(null, "Maxiumum quantity is " + c.rs.getInt(1) + " !!", "Warning", JOptionPane.WARNING_MESSAGE);
					}else {
						c.updateCart(qty, name, color);
						viewData();
						calculatePrice();	
						buttonCancel.setEnabled(false);
						buttonUpdate.setEnabled(false);
						spinQty.setEnabled(false);
						buttonEdit.setEnabled(true);
					}
					
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
		}else if (e.getSource() == buttonCancel) {
			buttonCancel.setEnabled(false);
			buttonUpdate.setEnabled(false);
			spinQty.setEnabled(false);
			buttonEdit.setEnabled(true);
			spinQty.setValue(0);
			txtColor.setText("");
			txtName.setText("");
		}else if (e.getSource() == buttonCheckOut) {
			Integer id = 1;
			String name = txtName.getText();
			String color = txtColor.getText();
			String check1 = "";
			ResultSet result;
			String query = "SELECT RIGHT(MAX(TransactionId), 3) FROM transactionheader";
			c.rs = c.executeQuery(query);
			try {
				if (c.rs.next()) {
					check1 = c.rs.getString(1);
				}
				if (check1 == null) {
					c.addTransactionHeader("TH001", userId, java.time.LocalDate.now().toString());
					query = "SELECT products.ProductID,cart.ProductQty FROM products,cart WHERE products.ProductID = cart.ProductId AND cart.UserId = '" + userId + "'";
					c.rs = c.executeQuery(query);
					while (c.rs.next()) {
						c.addTransactionDetail("TH001", c.rs.getString(1), c.rs.getInt(2));
					}
					JOptionPane.showMessageDialog(null, "Successfully checked out! View your transaction cart for further information.");
					c.removeCart(userId);
					this.dispose();
				}else {
					id = Integer.parseInt(check1) + 1;		
					query = "SELECT products.ProductID,cart.ProductQty FROM products,cart WHERE products.ProductID = cart.ProductId AND cart.UserId = '" + userId + "'";
					c.rs = c.executeQuery(query);
					if (id < 10) {
						c.addTransactionHeader("TH00" + id.toString(), userId, java.time.LocalDate.now().toString());
						while (c.rs.next()) {
							c.addTransactionDetail("TH00" + id.toString(), c.rs.getString(1), c.rs.getInt(2));
						}
					}else if (id < 100) {
						c.addTransactionHeader("TH0" + id.toString(), userId, java.time.LocalDate.now().toString());
						while (c.rs.next()) {
							c.addTransactionDetail("TH0" + id.toString(), c.rs.getString(1), c.rs.getInt(2));
						}
					}else if (id < 1000) {
						c.addTransactionHeader("TH" + id.toString(), userId, java.time.LocalDate.now().toString());
						while (c.rs.next()) {
							c.addTransactionDetail("TH" + id.toString(), c.rs.getString(1), c.rs.getInt(2));
						}
					}
					JOptionPane.showMessageDialog(null, "Successfully checked out! View your transaction cart for further information.");
					c.removeCart(userId);
					this.dispose();					
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (table.getSelectedRow() != -1) {

			txtName.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
			txtColor.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
	
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

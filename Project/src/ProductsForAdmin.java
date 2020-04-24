import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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


public class ProductsForAdmin extends JInternalFrame implements ActionListener, MouseListener{
	Vector<Vector<Object>> tableContent;
	Vector<Object> tableRow, tableColumn;
	
	SpinnerModel value = new SpinnerNumberModel(1, 0, 10, 1);
	
	DefaultTableModel dtm;
	
	JTable table;
	
	JScrollPane scrollPane;
	
	JLabel labelJudul;
	JLabel labelName;
	JLabel labelPrice;
	JLabel labelQty;
	JLabel labelColor;		
	
	JTextField txtName;
	JTextField txtPrice;
	JTextField txtColor;
	
	JSpinner spinQty = new JSpinner(value);
	
	JButton buttonAdd, buttonDelete;
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(2, 1));
	JPanel panelField = new JPanel(new GridLayout(0, 2, 5, 5));
	JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	Connect c = new Connect();
	
	public void addData(String id, String name, String color, int price, int qty) {
		tableRow = new Vector<>();
		tableRow.add(id.toString());
		tableRow.add(name);
		tableRow.add(color);
		tableRow.add(price);
		tableRow.add(qty);
		tableContent.add(tableRow);
	}
	
	public void viewData() {
		tableContent = new Vector<>();
		tableColumn = new Vector<>();
		
		tableColumn.add("Product ID");
		tableColumn.add("Product Name");
		tableColumn.add("Product Color");
		tableColumn.add("Product Price");
		tableColumn.add("Product Qty");
		
		String query = "SELECT * FROM products";
		c.rs = c.executeQuery(query);
		
		try {
			while (c.rs.next()) {
				addData(c.rs.getString(1), c.rs.getString(2), c.rs.getString(3), c.rs.getInt(4), c.rs.getInt(5));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		dtm = new DefaultTableModel(tableContent, tableColumn);
		table.setModel(dtm);
		
		
		dtm.fireTableDataChanged();
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
		
		labelJudul = new JLabel("Available Products: ");
		labelName = new JLabel("Name");
		labelPrice = new JLabel("Price");
		labelQty = new JLabel("Quantity");
		labelColor = new JLabel("Color");		
		
		txtName = new JTextField();
		txtPrice = new JTextField();
		txtColor = new JTextField();
		
		buttonAdd = new JButton("Add to cart");
		buttonDelete = new JButton("Delete Product");
		
		labelJudul.setFont(new Font("Serif", Font.BOLD, 20));
		
		table.addMouseListener(this);
		buttonAdd.addActionListener(this);
		buttonDelete.addActionListener(this);
		
	}
	
	public void generateField() {
		panelNorth.add(labelJudul);
		
		panelField.add(labelName);
		panelField.add(txtName);
		panelField.add(labelPrice);
		panelField.add(txtPrice);
		panelField.add(labelQty);
		panelField.add(spinQty);
		panelField.add(labelColor);
		panelField.add(txtColor);
		
		panelCenter.add(scrollPane);
		panelCenter.add(panelField);
		
		panelSouth.add(buttonAdd);
		panelSouth.add(buttonDelete);		
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);

	}

	public ProductsForAdmin() {
		initComponent();
		generateField();
		
		this.setTitle("Nama JUDULNYA");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(900, 600);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAdd) {
			Integer id = 1;
			String name = txtName.getText();
			String color = txtColor.getText();
			Integer qty = (Integer)spinQty.getValue();
			String check1 = "";
			if (qty <= 0) {
				JOptionPane.showMessageDialog(null, "The quantity must be more than zero!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else if (name.length() == 0 || color.length() == 0 || txtPrice.getText().length() == 0 || color.length() == 0) {
				JOptionPane.showMessageDialog(null, "All the fields must be filled.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else {
				Integer price = Integer.parseInt(txtPrice.getText());
				boolean check = false;
				String query = "SELECT * FROM products";
				c.rs = c.executeQuery(query);
				try {
					while (c.rs.next()) {
						if (name.equals(c.rs.getString(2)) && color.equals(c.rs.getString(3))) {
							c.updateProduct(name, color, price, qty);
							check = true;
						}
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (check == false) {
					query = "SELECT RIGHT(MAX(ProductId), 3) FROM products";
					c.rs = c.executeQuery(query);
					try {
						if (c.rs.next()) {
							check1 = c.rs.getString(1);
						}
						if (check1 == null) {
							c.addProduct("PD001", name, color, price, qty);
						}else {
							id = Integer.parseInt(c.rs.getString(1)) + 1;
							if (id < 10) {
								c.addProduct("PD00" + id.toString(), name, color, price, qty);
							}else if (id < 100) {
								c.addProduct("PD0" + id.toString(), name, color, price, qty);
							}else if (id < 1000) {
								c.addProduct("PD" + id.toString(), name, color, price, qty);
							}
						}
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Successfully updated the product!", "Message", JOptionPane.INFORMATION_MESSAGE);
				viewData();
			}
			
		}else if (e.getSource() == buttonDelete) {
			String name = txtName.getText();
			String color = txtColor.getText();
			Integer qty = (Integer)spinQty.getValue();
			if (qty <= 0) {
				JOptionPane.showMessageDialog(null, "The quantity must be more than zero!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else if (name.length() == 0 || color.length() == 0 || txtPrice.getText().length() == 0 || color.length() == 0) {
				JOptionPane.showMessageDialog(null, "All the fields must be filled.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else {
				boolean check = false;
				String query = "SELECT ProductName,ProductColor FROM products";
				c.rs = c.executeQuery(query);
				try {
					while (c.rs.next()) {
						if (name.equals(c.rs.getString(1)) && color.equals(c.rs.getString(2))) {
							c.deleteProduct(name, color);
							JOptionPane.showMessageDialog(null, "Successfully deleted 1 product(s)!", "Message", JOptionPane.INFORMATION_MESSAGE);
							check = true;
						}
						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (check == false) {
					JOptionPane.showMessageDialog(null, "Product doesn't exist, please re-input the data!", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				viewData();
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (table.getSelectedRow() != -1) {

			txtName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
			txtColor.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
			txtPrice.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
	
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

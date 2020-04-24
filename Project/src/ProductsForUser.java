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


public class ProductsForUser extends JInternalFrame implements ActionListener, MouseListener{
	String userId;
	
	Vector<Vector<Object>> tableContent;
	Vector<Object> tableRow, tableColumn;
	
	SpinnerModel value = new SpinnerNumberModel(1, 0, 10, 1);
	
	DefaultTableModel dtm;
	
	JTable table;
	
	JScrollPane scrollPane;
	
	JLabel labelJudul;
	JLabel labelId;
	JLabel labelName;
	JLabel labelPrice;
	JLabel labelQty;
	JLabel labelColor;		
	
	JTextField txtId;
	JTextField txtName;
	JTextField txtPrice;
	JTextField txtColor;
	
	JSpinner spinQty = new JSpinner(value);
	
	JButton buttonAdd;
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(2, 1));
	JPanel panelField = new JPanel(new GridLayout(0, 2));
	JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	Connect c = new Connect();
	
	public void addData(String id, String name, String color, int price, int qty) {
		tableRow = new Vector<>();
		tableRow.add(id);
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
		
		table.addMouseListener(this);
		
		labelJudul = new JLabel("Available Products: ");
		labelId = new JLabel("ID");
		labelName = new JLabel("Name");
		labelPrice = new JLabel("Price");
		labelQty = new JLabel("Quantity");
		labelColor = new JLabel("Color");		
		
		txtId = new JTextField();
		txtName = new JTextField();
		txtPrice = new JTextField();
		txtColor = new JTextField();
		
		buttonAdd = new JButton("Add to cart");
		
		labelJudul.setFont(new Font("Serif", Font.BOLD, 20));
		
		txtId.setEditable(false);
		txtName.setEditable(false);
		txtPrice.setEditable(false);
		txtColor.setEditable(false);
		
		buttonAdd.addActionListener(this);
		
	}
	
	public void generateField() {
		panelNorth.add(labelJudul);
		
		panelField.add(labelId);
		panelField.add(txtId);
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
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);

	}
	
	public void setId(String userId) {
		this.userId = userId;
		initComponent();
		generateField();
	}

	public ProductsForUser() {
		
		
		this.setTitle("Nama JUDULNYA");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(900, 600);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAdd) {
			String ProductId = txtId.getText();
			String name = txtName.getText();
			String color = txtColor.getText();
			Integer qty = (Integer)spinQty.getValue();
			Integer qtyDb = 0;
			String query = "SELECT ProductQty FROM products WHERE ProductId LIKE '" + ProductId + "'";
			c.rs = c.executeQuery(query);
			try {
				if (c.rs.next()) {
					qtyDb = c.rs.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (qty <= 0) {
				JOptionPane.showMessageDialog(null, "The quantity must be more than zero!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else if (qty > qtyDb) {
				JOptionPane.showMessageDialog(null, "There's only " + qtyDb + " product(s) available", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else if (name.length() == 0 || color.length() == 0 || txtPrice.getText().length() == 0 || color.length() == 0) {
				JOptionPane.showMessageDialog(null, "All the fields must be filled.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else {
				query = "SELECT * FROM cart";
				boolean check = false;
				c.rs = c.executeQuery(query);
				try {
					while (c.rs.next()) {
						if (ProductId.equals(c.rs.getString(2)) && userId.equals(c.rs.getString(1))) {
							c.updateCart(userId, ProductId, qty);
							check = true;
							JOptionPane.showMessageDialog(null, "Product(s) Updated to cart!" , "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
					if (check == false) {
						c.addCart(userId, ProductId, qty);
						JOptionPane.showMessageDialog(null, "Product(s) added to cart!" , "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (table.getSelectedRow() != -1) {

			txtId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
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

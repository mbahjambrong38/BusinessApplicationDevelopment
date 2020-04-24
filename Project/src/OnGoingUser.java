import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import jdk.nashorn.internal.scripts.JO;

public class OnGoingUser extends JInternalFrame implements MouseListener,ActionListener{
	
	JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 5, 5);  
	
	String userId;
	
	Vector<Vector<Object>> tableContent;
	Vector<Object> tableRow, tableColumn;
	
	SpinnerModel value = new SpinnerNumberModel(1, 0, 10, 1);
	
	DefaultTableModel dtm;
	
	JTable table;
	
	JScrollPane scrollPane;
	
	JLabel labelTitle;
	
	JButton buttonSubmit;
	
	JLabel labelRateTransaction;
	
	Hashtable labelTable; 
	
	Connect c = new Connect();
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelCenter = new JPanel(new GridLayout(0,1));
	JPanel panelField = new JPanel(new GridLayout(1,3));
	JPanel panelRateTransaction = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelSlider = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel panelSubmit = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	
	public void addData(String id, String date, int price , String status) {
		tableRow = new Vector<>();
		tableRow.add(id);
		tableRow.add(date);
		tableRow.add(price);
		tableRow.add(status);
		tableContent.add(tableRow);
	}
	
	public void viewData() {
		tableContent = new Vector<>();
		tableColumn = new Vector<>();
		
		tableColumn.add("Transaction ID");
		tableColumn.add("Transaction Date");
		tableColumn.add("Total Price");
		tableColumn.add("Status");
		
		System.out.println(userId);
		
		String query = "SELECT tr.TransactionId, tr.TransactionDate, SUM(ps.ProductPrice * tl.ProductQty) AS ProductPrice, tr.TransactionStatus FROM transactionheader tr, transactiondetail tl, products ps WHERE tr.TransactionId = tl.TransactionID AND tl.ProductID = ps.ProductID AND tr.TransactionStatus = 'On going' AND tr.UserId = '" + userId + "' GROUP BY tr.TransactionId";
		c.rs = c.executeQuery(query);
		
		try {
			while (c.rs.next()) {
				addData(c.rs.getString(1), c.rs.getString(2), c.rs.getInt(3), c.rs.getString(4));
				
			}
		} catch (SQLException e) {
			
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
		
		labelTitle = new JLabel("On Going Transaction: ");
		
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		
		labelTable  = new Hashtable();
		
		labelTable.put(new Integer(1), new JLabel("1")); 
		labelTable.put(new Integer(2), new JLabel("2"));
		labelTable.put(new Integer(3),  new JLabel("3"));
		labelTable.put(new Integer(4),  new JLabel("4"));
		labelTable.put(new Integer(5),  new JLabel("5"));
		
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		
		buttonSubmit = new JButton("Submit");
		
		labelRateTransaction = new JLabel("Rate Transaction");
		
		table.addMouseListener(this);
		buttonSubmit.addActionListener(this);
	}
	
	public void setComponent() {
		
		panelRateTransaction.add(labelRateTransaction);
		panelSlider.add(slider);
		panelSubmit.add(buttonSubmit);
		panelField.add(panelRateTransaction);
		panelField.add(panelSlider);
		panelField.add(panelSubmit);
		
		panelField.setVisible(false);
		
		panelNorth.add(labelTitle);
		panelCenter.add(scrollPane);
		panelCenter.add(panelField);
		
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
	}
	
	public void setId(String userId) {
		this.userId = userId;
		initComponent();
		setComponent();
	}
	
	public OnGoingUser() {
	
		
		this.setTitle("Nama JUDULNYA");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(900, 400);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (table.getSelectedRow() != -1) {
			int result = JOptionPane.showConfirmDialog(null, "Have you received the item(s)?", "Orded Confirmation", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "Please rate your transaction..");
				panelField.setVisible(true);
				
				
			}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonSubmit) {
			c.updateOnGoingTransaction(table.getValueAt(table.getSelectedRow(), 0).toString(), slider.getValue());
			JOptionPane.showMessageDialog(null, "Finished!!");
			this.dispose();
		}
		
	}

}

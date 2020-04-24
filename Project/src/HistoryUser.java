import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class HistoryUser extends JInternalFrame {
	
	String userId;
	
	Vector<Vector<Object>> tableContent;
	Vector<Object> tableRow, tableColumn;
	
	SpinnerModel value = new SpinnerNumberModel(1, 0, 10, 1);
	
	DefaultTableModel dtm;
	
	JTable table;
	
	JScrollPane scrollPane;
	
	JLabel labelTitle;
	
	Connect c = new Connect();
	
	JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	public void addData(String id, String date, int price , int rating) {
		tableRow = new Vector<>();
		tableRow.add(id);
		tableRow.add(date);
		tableRow.add(price);
		tableRow.add(rating);
		tableContent.add(tableRow);
	}
	
	public void viewData() {
		tableContent = new Vector<>();
		tableColumn = new Vector<>();
		
		tableColumn.add("Transaction ID");
		tableColumn.add("Transaction Date");
		tableColumn.add("Total Price");
		tableColumn.add("Rating");
		
		String query = "SELECT tr.TransactionId, tr.TransactionDate, SUM(ps.ProductPrice * tl.ProductQty) AS ProductPrice, tr.TransactionRating FROM transactionheader tr, transactiondetail tl, products ps WHERE tr.TransactionId = tl.TransactionID AND tl.ProductID = ps.ProductID AND tr.TransactionStatus = 'Finish' AND tr.UserId = '" + userId + "' GROUP BY tr.TransactionId";
		c.rs = c.executeQuery(query);
		
		try {
			while (c.rs.next()) {
				addData(c.rs.getString(1), c.rs.getString(2), c.rs.getInt(3), c.rs.getInt(4));
				
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
		
		labelTitle = new JLabel("Finished Transaction: ");
	}
	
	public void setComponent() {
		panelNorth.add(labelTitle);
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void setId(String userId) {
		this.userId = userId;
		initComponent();
		setComponent();
	}
	
	public HistoryUser() {
		
		this.setTitle("Nama JUDULNYA");
		this.setClosable(true);
		this.setResizable(false);
		this.setSize(900, 300);
		this.setVisible(true);
	}

}

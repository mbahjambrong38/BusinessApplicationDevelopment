import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

    public Statement stmt;
    public ResultSet rs;
    public Connection con;

    public Connect() {
        try{  
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");  
            stmt = con.createStatement();  
            
            System.out.println("Connected to the database..");
        }catch(Exception e){ 
            System.out.println(e);
        }  
    }
    

    public ResultSet executeQuery(String query){
        try{
            rs = stmt.executeQuery(query);
        }
        catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }

    public void executeUpdate(String query){
        try{
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void insertAccount(String id ,String email, String password, String dob, String gender, String address){
    	try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?,'user')");
			ps.setString(1, id);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, dob);
			ps.setString(5, gender);
			ps.setString(6, address);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void updateProfile(String password, String dob, String gender, String address, String userId) {
    	try {
			PreparedStatement ps = con.prepareStatement("UPDATE user SET UserPassword=?,UserDOB=?,UserGender=?,UserAddress=? WHERE UserId=?");
			ps.setString(1, password);
			ps.setString(2, dob);
			ps.setString(3, gender);
			ps.setString(4, address);
			ps.setString(5, userId);			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void updateProduct(String name, String color, int price, int qty){
    	try {
			PreparedStatement ps = con.prepareStatement("UPDATE products SET ProductPrice=?,ProductQty=? WHERE ProductName LIKE ? AND ProductColor LIKE ?");
			ps.setInt(1, price);
			ps.setInt(2, qty);
			ps.setString(3, name);
			ps.setString(4, color);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void addProduct(String id, String name, String color, int price, int qty) {
    	try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO products VALUES(?,?,?,?,?)");
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, color);
			ps.setInt(4, price);
			ps.setInt(5, qty);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void deleteProduct(String name, String color) {
    	try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE ProductName LIKE ? AND ProductColor LIKE ?");
			ps.setString(1, name);
			ps.setString(2, color);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void addCart(String UserId, String ProductId, Integer qty) {
    	try {
    		PreparedStatement ps = con.prepareStatement("INSERT INTO cart VALUES(?,?,?)");
    		ps.setString(1, UserId);
			ps.setString(2, ProductId);
			ps.setInt(3, qty);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void updateCart(String UserId, String ProductId, Integer qty) {
    	try {
    		PreparedStatement ps = con.prepareStatement("UPDATE cart SET ProductQty=(ProductQty+?) WHERE UserId LIKE ? AND ProductId LIKE ?");
    		ps.setInt(1, qty);
			ps.setString(2, UserId);
			ps.setString(3, ProductId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void updateCart(Integer qty, String name, String color) {
    	try {
    		PreparedStatement ps = con.prepareStatement("UPDATE cart SET ProductQty=? WHERE ProductID = (SELECT ProductID FROM products WHERE products.ProductName = ? AND products.ProductColor = ?)");
    		ps.setInt(1, qty);
			ps.setString(2, name);
			ps.setString(3, color);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void deleteCart(String name, String color) {
    	try {
    		PreparedStatement ps = con.prepareStatement("DELETE FROM cart WHERE ProductID LIKE (SELECT ProductID FROM products WHERE products.ProductName = ? AND products.ProductColor = ?)");
    		ps.setString(1, name);
			ps.setString(2, color);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void removeCart(String userId) {
    	try {
    		PreparedStatement ps = con.prepareStatement("DELETE FROM cart WHERE userId = ?");
    		ps.setString(1, userId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void addTransactionHeader(String transactionId, String userId, String date) {
    	try {
    		PreparedStatement ps = con.prepareStatement("INSERT INTO transactionheader VALUES(?,?,'On going',null,?)");
    		ps.setString(1, transactionId);
			ps.setString(2, userId);
			ps.setString(3, date);			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void addTransactionDetail(String transactionId, String productId, int productQty) {
    	try {
    		PreparedStatement ps = con.prepareStatement("INSERT INTO transactiondetail VALUES(?,?,?)");
    		ps.setString(1, transactionId);
			ps.setString(2, productId);
			ps.setInt(3, productQty);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void updateOnGoingTransaction(String transactionId, int rating) {
    	try {
    		PreparedStatement ps = con.prepareStatement("UPDATE transactionheader SET transactionStatus='Finish',transactionRating=? WHERE transactionId = ?");
    		ps.setInt(1, rating);
    		ps.setString(2, transactionId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addRememberMe(String userId) {
    	try {
    		PreparedStatement ps = con.prepareStatement("INSERT INTO lastlogin VALUES(?)");
    		ps.setString(1, userId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void removeRememberMe() {
    	try {
    		PreparedStatement ps = con.prepareStatement("DELETE FROM lastlogin");
    		ps.execute();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
}
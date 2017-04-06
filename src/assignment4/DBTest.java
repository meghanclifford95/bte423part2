package assignment4;

import java.sql.*;
import java.text.NumberFormat;

public class DBTest {

    public static void main(String args[]) {

        // Load the database driver
        // NOTE: This block is necessary for Oracle 10g (JDBC 3.0) or earlier,
        // but not for Oracle 11g (JDBC 4.0) or later
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        // define common JDBC objects
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            String dbUrl = "jdbc:mysql://146.148.76.28:3306/Hotel?verifyServerCertificate=false&useSSL=false";
            String username = "meghan";
            String password = "Violet9!";
            connection = DriverManager.getConnection(
                    dbUrl, username, password);
     
           // Query1
            statement = connection.createStatement();
            String query
                    = "SELECT * FROM Hotel";
            rs = statement.executeQuery(query);
            System.out.println("Hotel List:\n");
            while (rs.next()) {
                int hotel_no = rs.getInt("hotelNo");
                String hotel_name = rs.getString("hotelName");
                String hotel_city = rs.getString("city");

                System.out.println(
                      "Hotel Number:     " + hotel_no + "\n"
                    + "Hotel Name: " + hotel_name + "\n"
                    + "City:      " + hotel_city + "\n");
            }
            
            //Query2
            statement = connection.createStatement();
            String query2
                    = "SELECT * FROM Hotel WHERE city='London'";
            rs = statement.executeQuery(query2);

            System.out.println("Hotels in London:\n");
            while (rs.next()) {
                int hotel_no = rs.getInt("hotelNo");
                String hotel_name = rs.getString("hotelName");
                String hotel_city = rs.getString("city");

                System.out.println(
                      "Hotel Number:     " + hotel_no + "\n"
                    + "Hotel Name: " + hotel_name + "\n"
                    + "City:      " + hotel_city + "\n");
            } 
            
            
            //Query3
            statement = connection.createStatement();
            String query3
                    = "SELECT guestName, guestAddress "
                    		+ "FROM Guest "
                    		+ "WHERE guestAddress LIKE '%London%'"
                    		+ "ORDER BY guestName";
            rs = statement.executeQuery(query3);

            System.out.println("Guests in London:\n");
            while (rs.next()) {
                String guest_name = rs.getString("guestName");
                String guest_address = rs.getString("guestAddress");

                System.out.println(
                     "Guest Name: " + guest_name + "\n"
                    + "Guest Address:      " + guest_address + "\n");
            }      
            
            
            //Query4
            statement = connection.createStatement();
            String query4
                    = "SELECT AVG(price)"
                    		+ "FROM Room";
            rs = statement.executeQuery(query4);

            System.out.println("Average price of rooms:\n");
            while (rs.next()) {
                System.out.println(
                     "Avg Price:      " + rs.getFloat(1) + "\n");
            } 
            
            
            //Query5
            statement = connection.createStatement();
            String query5
                    = "SELECT price, type FROM Hotel h, Room r WHERE h.hotelName='Grosvenor' AND h.hotelNo=r.hotelNo";
            rs = statement.executeQuery(query5);

            System.out.println("Grosvenor Hotel rooms available:\n");
            while (rs.next()) {              
                String price = rs.getString("price");
                String type = rs.getString("type");

                System.out.println(
                      "Price:     " + price + "\n"
                    + "Type: " + type + "\n");
            } 
            
            //Query6
            statement = connection.createStatement();
            String query6
                    = "SELECT SUM(r.price) FROM Booking b, Room r, Hotel h WHERE h.hotelName='Grosvenor' AND r.hotelNo=h.hotelNo AND r.roomNo=b.roomNo AND (dateFrom<=CURRENT_DATE AND dateTo>=CURRENT_DATE)";
            rs = statement.executeQuery(query6);

            System.out.println("Sum of current bookings:\n");
            while (rs.next()) {
                System.out.println(
                     "Total Income:      " + rs.getFloat(1) + "\n");
            } 
            
            //Query7
            statement = connection.createStatement();
            String query7
                    = "SELECT COUNT(*), h.hotelName FROM Hotel h, Room r WHERE h.hotelNo=r.hotelNo AND city='London' GROUP BY hotelName";
            rs = statement.executeQuery(query7);

            System.out.println("Count hotels in London by name:\n");
            while (rs.next()) {
                String hotel_name = rs.getString("h.hotelName");

                System.out.println(
                      "Hotel Name:     " + hotel_name + "\n"
                    + "Hotel Name: " + rs.getFloat(1) + "\n");
            } 
            
            
            
            //Query8
            statement = connection.createStatement();
            String query8
                    = "SELECT COUNT(type) AS count, type FROM Room r, Hotel h, Booking b WHERE city='London' AND b.hotelNo=h.hotelNo AND r.roomNo=b.roomNo GROUP BY type";
            rs = statement.executeQuery(query8);

            System.out.println("Maximum number of rooms available (of the same type):\n");
            float max= (float) 0;
            while (rs.next()) {
                String type = rs.getString("type");
                float count= rs.getFloat(1);
                
                if(count > max){
                	max=count; 
                }
                

                System.out.println(
                		"Count: " + count + "\n"
                			+	"Type: " + type + "\n");
            } 
            System.out.println(
            		"Max: " + max + "\n");
           
            
            
            
            
            
            //Query9
            statement = connection.createStatement();
            String query9
                    = "UPDATE Room SET price=price*1.05";
            statement.executeUpdate(query9);
            
            statement = connection.createStatement();
            String query10
                    = "SELECT price FROM Room";
            rs = statement.executeQuery(query10);

            System.out.println("Updated Prices:\n");
            while (rs.next()) {
                String price = rs.getString("price");

                System.out.println(
                      "Updated Price:     " + price + "\n");
            } 
   
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}

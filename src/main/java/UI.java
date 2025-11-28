import java.util.*;
import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class UI {
    private String user;
    private String password;

    public UI(String user, String password) {
        this.user = user;
        this.password = password;
    }

    MysqlDataSource dataSource = new MysqlDataSource();
    Scanner scanner = new Scanner(System.in);

    public void HomeScreen(){
        boolean in = true;
        while(in){
            System.out.println("""
                
                What do you want to do?
                    1) Display all products
                    2) Display all customers
                    0) Exit
                Select an option:
                """);
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice){
                case 1 -> displayProdcuts();
                case 2 -> displayCustomers();
                case 0 -> {
                    System.out.println("Exiting program...Good bye!");
                    in = false;
                }
            }
        }
    }

    public void displayProdcuts(){

        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = this.user;
        String password = this.password;

        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("""
                        ID   Name           Price    Stock  
                        ---- -------------- -------  ------ 
                        """);

            while (rs.next()) {
                String ProductID = rs.getString("ProductID");
                String ProductName = rs.getString("ProductName");
                String UnitPrice = rs.getString("UnitPrice");
                String UnitsInStock = rs.getString("UnitsInStock");

                System.out.printf("%-4s %-14s %-8s %1s %n", ProductID, ProductName, UnitPrice, UnitsInStock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayCustomers(){

        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = this.user;
        String password = this.password;

        String sql = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers";

        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("""
                        ContactName      CompanyName             City       Country    Phone
                        ---------------- ----------------------- ---------- ---------- -------------
                        """);

            while (rs.next()) {
                String ContactName = rs.getString("ContactName");
                String CompanyName = rs.getString("CompanyName");
                String City = rs.getString("City");
                String Country = rs.getString("Country");
                String Phone = rs.getString("Phone");

                System.out.printf("%-16s %-23s %-11s %-6s %13s %n", ContactName, CompanyName, City, Country, Phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

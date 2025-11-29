import java.util.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class UI {
    private final String user;
    private final String password;

    public UI(String user, String password) {
        this.user = user;
        this.password = password;
    }

    BasicDataSource dataSource = new BasicDataSource();
    Scanner scanner = new Scanner(System.in);

    public void HomeScreen(){
        boolean in = true;
        while(in){
            System.out.println("""
                
                What do you want to do?
                    1) Display all products
                    2) Display all customers
                    3) Display all categories
                    0) Exit
                Select an option:
                """);
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice){
                case 1 -> displayProducts();
                case 2 -> displayCustomers();
                case 3-> displayAllCategories();
                case 0 -> {
                    System.out.println("Exiting program...Good bye!");
                    in = false;
                }
            }
        }
    }

    public void displayProducts(){
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = this.user;
        String password = this.password;

        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("""
                        ID   Name           Price    Stock
                        ---- -------------- -------  ------""");

            while (rs.next()) {
                String ProductID = rs.getString("ProductID");
                String ProductName = rs.getString("ProductName");
                double UnitPrice = rs.getDouble("UnitPrice");
                int UnitsInStock = rs.getInt("UnitsInStock");

                System.out.printf("%-4s %-14s %-8.2f %1s %n", ProductID, ProductName, UnitPrice, UnitsInStock);
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

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("""
                        ContactName      CompanyName             City       Country    Phone
                        ---------------- ----------------------- ---------- ---------- -------------""");

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

    public void displayAllCategories(){
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = this.user;
        String password = this.password;

        String sql = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            System.out.println("""
                        ID  Name
                        --- --------------""");

            while(rs.next()) {
                String CategoryID = rs.getString("CategoryId");
                String CategoryName = rs.getString("CategoryName");

                System.out.printf("%-3s %-14s %n", CategoryID, CategoryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Select a Category ID to search for products by.");
        int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("""
                        ID   Name                   Price    Stock
                        ---- ---------------------- -------  ------""");

        String sql1 = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products
                JOIN categories ON products.CategoryID = categories.CategoryID
                WHERE products.CategoryID = ?""";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1)){

            ps.setInt(1, choice);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ProductID = rs.getString("ProductID");
                    String ProductName = rs.getString("ProductName");
                    double UnitPrice = rs.getDouble("UnitPrice");
                    int UnitsInStock = rs.getInt("UnitsInStock");

                    System.out.printf("%-4s %-22s %-8.2f %1d %n", ProductID, ProductName, UnitPrice, UnitsInStock);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

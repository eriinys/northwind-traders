import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class main {
    public static void main(String[] args){

        if (args.length != 2) {
            System.out.println("Application needs two arguments to run.");
            System.exit(1); //exits program when required arguments are not provided with exit code 1
        }

        MysqlDataSource dataSource = new MysqlDataSource();

        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = args[0];
        String password = args[1];

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

                System.out.printf("%-4s %-14s %-8s %1s \n", ProductID, ProductName, UnitPrice, UnitsInStock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

 /*
         try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql + " WHERE ProductName LIKE ?")) {

            ps.setString(1, "p%");

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ProductName = rs.getString("ProductName");
                    System.out.println(ProductName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         */
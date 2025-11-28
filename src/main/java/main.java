import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class main {

    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println("Application needs two username and password to run.");
            System.exit(1); //exits program when required arguments are not provided with exit code 1
        }

        UI ui = new UI(args[0], args[1]);
        ui.HomeScreen();
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
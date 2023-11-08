package DatabaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public static Connection databaseLink;

    public static Connection getConnection() {
        String databaseUser = "root";
        String databasePassword = "hollow29117.z";
        String url =  "jdbc:mysql://localhost:3306/user_account";

        try {
            System.out.println("Connecting to database :" + url);
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }
}

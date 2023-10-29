package DatabaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseUser = "root";
        String databasePassword = "";
        String url =  "jdbc:mysql://127.0.0.1:3306/user_account";

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

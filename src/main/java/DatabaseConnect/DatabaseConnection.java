package DatabaseConnect;

import Controllers.UserInfo;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Controllers.PreloaderController.connectDB;

public class DatabaseConnection {
    public static Connection databaseLink;

    public static int userId;

    public static Connection getConnection() {
        String databaseUser = "sql12662361";
        String databasePassword = "RKMWwvNPNS";
        String url = "jdbc:mysql://sql12.freesqldatabase.com/sql12662361";

        try {
            System.out.println("Connecting to database :" + url);
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

    public static boolean validateLogin(String username, String password) {
//        Connection connectDB = DatabaseConnection.getConnection();
        String verifyLogin = "SELECT COUNT(1) FROM useraccounts WHERE username='" + username + "' AND password='" + password + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validateSignUp(String username, String password, String confirmPassword, String fName, String lName) {
//        Connection connectDB = DatabaseConnection.getConnection();

        String verifySignUp = "SELECT COUNT(1) FROM useraccounts WHERE username='" + username + "' AND password='" + password + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifySignUp);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1 && password.equals(confirmPassword)) {
                    return false;
                } else {
                    if (password.equals(confirmPassword)) {
                        String initUser = "INSERT INTO useraccounts (FirstName, LastName, Username, Password, profileImage) VALUES ('" + fName + "', '" + lName + "', '" + username + "', '" + password + "', null)";
                        statement.executeUpdate(initUser);
                        int userId = getUserId(username);
                        String initUsage = "INSERT INTO appusage (UserID, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday) VALUES ('" + userId + "',0,0,0,0,0,0,0)";
                        statement.executeUpdate(initUsage);
//                        String initFavourite = "INSERT INTO favourite (UserID, WordTarget) VALUES ('" + fName + "', '" + lName + "', '" + username + "', '" + password + "')";
//                        statement.executeUpdate(initFavourite);
                        //To do
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UserInfo getUserInfo(String username) {
        String query = "SELECT iduseraccounts, CONCAT(FirstName,' ', LastName) as FullName, profileImage " +
                "FROM useraccounts WHERE username = '" + username + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if (queryResult.next()) {
                userId = queryResult.getInt("Iduseraccounts");
                String name = queryResult.getString("FullName");
                byte[] profileImg = queryResult.getBytes("profileImage");
//                String usageTime = queryResult.getString("Time");
                return new UserInfo(userId, name, username, profileImg);
            } else {
                System.out.println("User not found!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static int getUserId(String username) {
        String query = "SELECT iduseraccounts FROM useraccounts WHERE Username = '" + username +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if (queryResult.next()) {
                return queryResult.getInt("iduseraccounts");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateProfilePicture(int userId, String path) {
        String query = "UPDATE useraccounts set profileImage = LOAD_FILE('" + path + "') WHERE iduseraccounts = " + userId;
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTimeUsage(int userId, String dayOfWeek, int addTime) {
        String query = "UPDATE appusage SET " + dayOfWeek + " = " + addTime + " WHERE UserId = '" + userId + "'";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getTimeUsage(int userId) {
        Map<String, Integer> usageMap = new HashMap<>();
        String query = "SELECT Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday FROM appusage WHERE UserId = " + userId;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                usageMap.put("Monday", resultSet.getInt("Monday"));
                usageMap.put("Tuesday", resultSet.getInt("Tuesday"));
                usageMap.put("Wednesday", resultSet.getInt("Wednesday"));
                usageMap.put("Thursday", resultSet.getInt("Thursday"));
                usageMap.put("Friday", resultSet.getInt("Friday"));
                usageMap.put("Saturday", resultSet.getInt("Saturday"));
                usageMap.put("Sunday", resultSet.getInt("Sunday"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usageMap;
    }
    public static void getFavouriteList(int userId, List<String> list) {
        String query = "SELECT WordTarget FROM favourite WHERE UserId = " + userId;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                list.add(resultSet.getString("WordTarget"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFavouriteWord(int userId, String wordTarget) {
        String query = "INSERT INTO favourite(UserId,WordTarget) VALUES (" + userId + ",'" + wordTarget + "')";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFavouriteWord(int userId, String wordTarget) {
        String query = "DELETE FROM favourite WHERE UserID = "+ userId +" AND  WordTarget = '" + wordTarget + "'";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

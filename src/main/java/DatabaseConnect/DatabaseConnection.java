package DatabaseConnect;

import Base.UserInfo;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        String query = "SELECT idUserAccounts, CONCAT(FirstName,' ', LastName) as FullName, profileImage " +
                "FROM useraccounts WHERE username = '" + username + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if (queryResult.next()) {
                userId = queryResult.getInt("IdUserAccounts");
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
        String query = "SELECT idUserAccounts FROM useraccounts WHERE Username = '" + username +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if (queryResult.next()) {
                return queryResult.getInt("idUserAccounts");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void updateProfilePicture(int userId, Blob blob) {
        String query = "UPDATE useraccounts SET profileImage = ? WHERE idUserAccounts = ?";
        try {
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setBlob(1, blob);
            statement.setInt(2, userId);

            statement.executeUpdate();
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

    public static void updateProblemSolved(int userId, String word) {
        String query = "INSERT INTO game(UserId, ProblemSolved) VALUES( " + userId + ",'" + word +"')";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int countProblemSolved(int userId) {
        String query = "SELECT COUNT(DISTINCT ProblemSolved) as count FROM game WHERE UserId = " + userId + " ORDER BY UserId";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) return resultSet.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static TreeMap<String,String> getListEditWord(int userId) {
        TreeMap<String,String> list = new TreeMap<>();
        String query = "SELECT NewWordTarget,NewWordExplain FROM editword WHERE UserId = " + userId;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String newWordTarget = resultSet.getString("NewWordTarget");
                String newWordExplain = resultSet.getString("NewWordExplain");
                list.put(newWordTarget,newWordExplain);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addEditWord(int userId, String oldWordTarget,String oldWordExplain, String newWordTarget,String newWordExplain) {
        String query1 = "SELECT COUNT(OldWordTarget) as count FROM editword WHERE UserId = "+ userId +"  AND OldWordTarget = '" + oldWordTarget + "' GROUP BY UserId";
        System.out.println(oldWordExplain);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query1);
            if (!resultSet.next()) {
                String query = "INSERT INTO editword(UserId, OldWordTarget, OldWordExplain, NewWordTarget, NewWordExplain) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connectDB.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, oldWordTarget);
                preparedStatement.setString(3, oldWordExplain);
                preparedStatement.setString(4, newWordTarget);
                preparedStatement.setString(5, newWordExplain);

                preparedStatement.executeUpdate();
            } else {
                String query3 = "UPDATE editword SET NewWordTarget = '" + newWordTarget + "', NewWordExplain = '" + newWordExplain + "' WHERE UserId = " + userId;
                statement.executeUpdate(query3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateDeleteWord(int userId, String word) {
        String query = "INSERT INTO deleteword(UserId,Word) VALUES (" + userId + ",'" + word + "')";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDeleteWord(int userId) {
        List<String> list = new ArrayList<>();
        String query = "SELECT Word FROM deleteword WHERE UserId = " + userId;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString("Word"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateAddWord(int userId,String wordTarget, String wordExplain) {
        String query = "INSERT INTO addword(UserId,WordTarget,WordExplain) VALUES (" + userId + ",'" + wordTarget + "','" + wordExplain +"')";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TreeMap<String,String> getAddWord(int userId) {
        TreeMap<String,String> list = new TreeMap<>();
        String query = "SELECT WordTarget, WordExplain FROM addword WHERE UserId = " + userId;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String temp1 = resultSet.getString("WordTarget");
                String temp2 = resultSet.getString("WordExplain");
                list.put(temp1, temp2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void startWeeklyReset(int userId) {
        String query = "SELECT DayOfMonday FROM appusage WHERE UserId = " + userId;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                java.sql.Date day = (resultSet.getDate("DayOfMonday"));
//                System.out.println(day.toString());

                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                if (dayOfWeek == Calendar.MONDAY) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(calendar.getTime());
                    System.out.println("Reset Time");
                    if(!day.toString().equals(formattedDate)) {
                        statement.executeUpdate("UPDATE appusage SET Monday = 0, Tuesday = 0, Wednesday = 0, Thurday = 0, Friday = 0, Saturday = 0, Sunday = 0, DayOfMonday = '" + formattedDate + "' WHERE UserId = " + userId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

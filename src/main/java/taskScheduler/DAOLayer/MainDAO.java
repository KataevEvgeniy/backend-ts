package taskScheduler.config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/users";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> index(){
        List<String> strs = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM employee");
            while (result.next()){
                System.out.println(result.getInt("id")+result.getString("firstname"));
                strs.add(result.getString("id"));
                strs.add(result.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return strs;
    }
}

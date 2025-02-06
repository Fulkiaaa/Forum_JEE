package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://srv1590.hstgr.io:3306/u956619994_forumjee"; 
    private static final String USER = "u956619994_master"; 
    private static final String PASSWORD = "Manu2025?";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // Charge le driver JDBC
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver MariaDB introuvable !");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

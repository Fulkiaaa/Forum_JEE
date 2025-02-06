package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:mariadb://srv1590.hstgr.io:3306/u956619994_forumjee";
    private static final String DB_USERNAME = "u956619994_master";
    private static final String DB_PASSWORD = "Manu2025?"; 

    // Connexion unique (optionnel)
    private static Connection connection = null;

    // Méthode pour obtenir une connexion
    public static Connection getConnection() throws SQLException {
        try {
            // Chargement du driver dans la méthode
            Class.forName("org.mariadb.jdbc.Driver");
            
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erreur : Driver MariaDB introuvable !", e);
        }
    }

    // Méthode pour fermer la connexion
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // URL de la base de données
	// URL de la base de données, remplacez "votre_bdd" par le nom réel de votre base de données
	private static final String DB_URL = "jdbc:mariadb://srv1590.hstgr.io:3306/u956619994_forumjee";  
	private static final String DB_USERNAME = "u956619994_master";  // Modifiez avec votre utilisateur
	private static final String DB_PASSWORD = "Manu2025?";  // Modifiez avec votre mot de passe


    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        try {
        	Class.forName("org.mariadb.jdbc.Driver");

            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver de la base de données introuvable.", e);
        }
    }
}

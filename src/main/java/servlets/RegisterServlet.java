package servlets;

import utils.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données du formulaire
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Utiliser la classe DBConnection pour obtenir la connexion
            Connection conn = DBConnection.getConnection();

            // Requête d'insertion SQL
            String query = "INSERT INTO utilisateurs (nom_utilisateur, email, mot_de_passe) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password); // Pensez à sécuriser le mot de passe

            // Exécuter la requête
            int result = statement.executeUpdate();

            // Vérifier si l'utilisateur a été ajouté
            if (result > 0) {
                response.getWriter().println("Inscription réussie !");
            } else {
                response.getWriter().println("Erreur lors de l'inscription.");
            }

            // Fermer la connexion
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erreur : " + e.getMessage());
        }
    }
}

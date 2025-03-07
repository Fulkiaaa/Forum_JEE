package servlets;

import models.User;
import utils.DBConnection;
import utils.PasswordUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Récupérer les paramètres du formulaire d'inscription
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        
        try {           
            pwd = PasswordUtils.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'encryption.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        
        // Créer un objet User avec les données récupérées
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pwd);

        try {
            // Connexion à la base de données
            Connection conn = DBConnection.getConnection();

            // Requête d'insertion dans la base de données
            String query = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, pwd);  // Insérer le mot de passe sans hashage
            stmt.setString(3, user.getEmail());

            // Exécuter la requête et vérifier si l'utilisateur a bien été ajouté
            int result = stmt.executeUpdate();

            // Si l'inscription est réussie
            if (result > 0) {
                response.sendRedirect("connection.jsp?message=Inscription réussie !");
            } else {
                request.setAttribute("errorMessage", "Erreur lors de l'inscription.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

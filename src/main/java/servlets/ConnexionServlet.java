package servlets;

import utils.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Connexion réussie, on stocke l'utilisateur en session
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", username);
                response.sendRedirect("accueil.jsp"); // Redirection vers la page d'accueil
            } else {
                // Connexion échouée, message d'erreur
                request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect.");
                request.getRequestDispatcher("connexion.jsp").forward(request, response);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erreur : " + e.getMessage());
        }
    }
}

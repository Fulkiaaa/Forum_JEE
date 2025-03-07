package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import utils.DBConnection;
import models.User;

@SuppressWarnings("serial")
public class CreationMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	
        // Vérifier si l'utilisateur est connecté
        User utilisateur = (User) request.getSession().getAttribute("user");
        if (utilisateur == null) {
            response.sendRedirect("connection.jsp");
            return;
        }

        String contenu = request.getParameter("contenu");
        String idSujet = request.getParameter("idSujet");

        if (contenu == null || contenu.trim().isEmpty() || idSujet == null || idSujet.isEmpty()) {
            response.sendRedirect("sujet?id=" + idSujet + "&error=emptyMessage");
            return;
        }

        try {
        	conn = DBConnection.getConnection();
        			
            String query = "INSERT INTO messages (contenu_message, date_creation, id_sujet, id_utilisateur) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, contenu);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(3, Integer.parseInt(idSujet));
            stmt.setInt(4, utilisateur.getId());

            stmt.executeUpdate();

            response.sendRedirect("sujet?id=" + idSujet);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }finally {
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }	
    }
}

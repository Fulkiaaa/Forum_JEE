package servlets;

import models.Role;
import models.User;

import utils.DBConnection;
import utils.PasswordUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@SuppressWarnings("serial")
public class ConnectionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        
        try {            
            pwd = PasswordUtils.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'encryption.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        		
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT id_utilisateur, nom_utilisateur, role.id, role.nom_role FROM utilisateurs INNER JOIN role ON role.id = utilisateurs.id_role WHERE email = ? AND mot_de_passe = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, pwd);

            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id_utilisateur"), rs.getString("nom_utilisateur"), email, pwd, new Role(rs.getInt("role.id"), rs.getString("role.nom_role")));
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("home.jsp");
            } else {
                request.setAttribute("errorMessage", "Identifiants incorrects.");
                request.getRequestDispatcher("connection.jsp").forward(request, response);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("connection.jsp").forward(request, response);
        }finally {
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}
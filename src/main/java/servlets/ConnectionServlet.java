package servlets;

import models.User;

import utils.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("serial")
public class ConnectionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id_utilisateur"), rs.getString("nom_utilisateur"), email, password);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("errorMessage", "Identifiants incorrects.");
                request.getRequestDispatcher("connection.jsp").forward(request, response);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("connection.jsp").forward(request, response);
        }
    }
}
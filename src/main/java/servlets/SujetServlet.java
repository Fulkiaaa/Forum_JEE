package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import utils.DBConnection;
import models.Subject;
import models.User;
import models.Category;

@SuppressWarnings("serial")
public class SujetServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idSujet = request.getParameter("id");

        if (idSujet == null || idSujet.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        Subject sujet = null;

        try (Connection conn = DBConnection.getConnection()) {
            // Requête pour récupérer les informations du sujet avec son utilisateur et sa catégorie
            String query = "SELECT s.id_sujet, s.titre_sujet, s.contenu_sujet, s.date_creation, " +
                           "u.id_utilisateur, u.nom_utilisateur, u.email, u.mot_de_passe, " +
                           "c.id_categorie, c.nom_categorie " +
                           "FROM sujets s " +
                           "INNER JOIN utilisateurs u ON s.id_utilisateur = u.id_utilisateur " +
                           "INNER JOIN categories c ON s.id_categorie = c.id_categorie " +
                           "WHERE s.id_sujet = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(idSujet));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User utilisateur = new User(
                    rs.getInt("id_utilisateur"),
                    rs.getString("nom_utilisateur"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe")
                );

                Category categorie = new Category(
                    rs.getInt("id_categorie"),
                    rs.getString("nom_categorie")
                );

                sujet = new Subject(
                    rs.getInt("id_sujet"),
                    rs.getString("titre_sujet"),
                    rs.getString("contenu_sujet"),
                    rs.getDate("date_creation"),
                    categorie,
                    utilisateur
                );

                request.setAttribute("subject", sujet);
                RequestDispatcher dispatcher = request.getRequestDispatcher("sujet.jsp");
                dispatcher.forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

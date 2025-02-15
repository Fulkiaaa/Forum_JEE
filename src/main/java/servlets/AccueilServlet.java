package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DBConnection;
import models.Category;
import models.Subject;
import models.User;

@SuppressWarnings("serial")
public class AccueilServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ArrayList<Subject> subjects = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            // Requête pour récupérer les 3 derniers sujets
            String query = "SELECT s.id_sujet, s.titre_sujet, s.contenu_sujet, s.date_creation, " +
                           "c.id_categorie, c.nom_categorie, " +
                           "u.id_utilisateur, u.nom_utilisateur " +
                           "FROM sujets s " +
                           "INNER JOIN categories c ON s.id_categorie = c.id_categorie " +
                           "INNER JOIN utilisateurs u ON s.id_utilisateur = u.id_utilisateur " +
                           "ORDER BY s.date_creation DESC LIMIT 3";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Requête préparée : " + stmt.toString());

            
            while (rs.next()) {
                Category category = new Category(rs.getInt("id_categorie"), rs.getString("nom_categorie"));
                User user = new User(rs.getInt("id_utilisateur"), rs.getString("nom_utilisateur"), null, null);
                
                Subject subject = new Subject(
                    rs.getInt("id_sujet"),
                    rs.getString("titre_sujet"),
                    rs.getString("contenu_sujet"),
                    rs.getDate("date_creation"),
                    category,
                    user
                );
                
                subjects.add(subject);
            }
            
            // Passer les sujets récupérés à la page d'accueil
            request.setAttribute("subjects", subjects);
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

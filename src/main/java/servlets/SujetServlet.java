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
import models.Message;

@SuppressWarnings("serial")
public class SujetServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idSujet = request.getParameter("id");

        if (idSujet == null || idSujet.isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        Subject sujet = null;
        ArrayList<Message> messages = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            // Récupérer le sujet avec son auteur et sa catégorie
            String querySujet = "SELECT s.id_sujet, s.titre_sujet, s.contenu_sujet, s.date_creation, " +
                                "c.id_categorie, c.nom_categorie, " +
                                "u.id_utilisateur, u.nom_utilisateur, u.email, u.mot_de_passe " +
                                "FROM sujets s " +
                                "INNER JOIN categories c ON s.id_categorie = c.id_categorie " +
                                "INNER JOIN utilisateurs u ON s.id_utilisateur = u.id_utilisateur " +
                                "WHERE s.id_sujet = ?";
            
            PreparedStatement stmtSujet = conn.prepareStatement(querySujet);
            stmtSujet.setInt(1, Integer.parseInt(idSujet));
            ResultSet rsSujet = stmtSujet.executeQuery();
            
            if (rsSujet.next()) {
                Category categorie = new Category(rsSujet.getInt("id_categorie"), rsSujet.getString("nom_categorie"));
                User auteur = new User(
                    rsSujet.getInt("id_utilisateur"),
                    rsSujet.getString("nom_utilisateur"),
                    rsSujet.getString("email"),
                    rsSujet.getString("mot_de_passe")
                );
                
                sujet = new Subject(
                    rsSujet.getInt("id_sujet"),
                    rsSujet.getString("titre_sujet"),
                    rsSujet.getString("contenu_sujet"),
                    rsSujet.getDate("date_creation"),
                    categorie, auteur
                );
            }
            
            if (sujet == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            
            // Récupérer les messages associés à ce sujet
            String queryMessages = "SELECT m.id_message, m.contenu_message, m.date_creation, " +
                                   "u.id_utilisateur, u.nom_utilisateur, u.email, u.mot_de_passe " +
                                   "FROM messages m " +
                                   "INNER JOIN utilisateurs u ON m.id_utilisateur = u.id_utilisateur " +
                                   "WHERE m.id_sujet = ? ORDER BY m.date_creation ASC";
            
            PreparedStatement stmtMessages = conn.prepareStatement(queryMessages);
            stmtMessages.setInt(1, Integer.parseInt(idSujet));
            ResultSet rsMessages = stmtMessages.executeQuery();

            while (rsMessages.next()) {
                User auteurMessage = new User(
                    rsMessages.getInt("id_utilisateur"),
                    rsMessages.getString("nom_utilisateur"),
                    rsMessages.getString("email"),
                    rsMessages.getString("mot_de_passe")
                );
                
                Message message = new Message(
                    rsMessages.getInt("id_message"),
                    rsMessages.getString("contenu_message"),
                    rsMessages.getTimestamp("date_creation"),
                    sujet, auteurMessage
                );

                messages.add(message);
            }
            
            request.setAttribute("sujet", sujet);
            request.setAttribute("messages", messages);
            RequestDispatcher dispatcher = request.getRequestDispatcher("sujet.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        System.out.print("test : " + idSujet);
        if(idSujet == null) {
        	idSujet = (String)request.getAttribute("id");
        }

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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String idCategorie = request.getParameter("idCat");
        String titre = request.getParameter("title");
        String contenu = request.getParameter("content");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("connection.jsp");
            return;
        }
        
        String idUser = Integer.toString(user.getId());
                                  
        if (idCategorie == null || idCategorie.isEmpty()
                || titre == null || titre.isEmpty()
                || contenu == null || contenu.isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        String sujet = null;
        try (Connection conn = DBConnection.getConnection()) {
            String queryInsertSubject = "INSERT INTO sujets (titre_sujet, contenu_sujet, id_categorie, id_utilisateur) VALUES (?, ?, ?, ?);";
            try (PreparedStatement stmtInsertSubject = conn.prepareStatement(queryInsertSubject)) {
                stmtInsertSubject.setString(1, titre);
                stmtInsertSubject.setString(2, contenu);
                stmtInsertSubject.setInt(3, Integer.parseInt(idCategorie));
                stmtInsertSubject.setInt(4, Integer.parseInt(idUser));
                
                int rowsAffected = stmtInsertSubject.executeUpdate();

                if (rowsAffected > 0) {
                    String querySujet = "SELECT id_sujet FROM sujets WHERE titre_sujet = ? AND contenu_sujet = ? AND id_categorie = ? AND id_utilisateur = ?";
                    try (PreparedStatement stmtSujet = conn.prepareStatement(querySujet)) {
                        stmtSujet.setString(1, titre);
                        stmtSujet.setString(2, contenu);
                        stmtSujet.setInt(3, Integer.parseInt(idCategorie));
                        stmtSujet.setInt(4, Integer.parseInt(idUser));
                        
                        try (ResultSet rsSujets = stmtSujet.executeQuery()) {
                        	
                            if (rsSujets.next()) {                            	
                            	sujet = rsSujets.getString("id_sujet");
                            }
                        }
                    }
                }
            }
            request.setAttribute("id", sujet);
            doGet(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

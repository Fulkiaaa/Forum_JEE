package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import utils.DBConnection;
import models.Category;
import models.Subject;

@SuppressWarnings("serial")
public class SubjectCreationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idCategorie = request.getParameter("idCat");
        String titre = request.getParameter("titre");
        String contenu = request.getParameter("contenu");
        String idUser = request.getParameter("idUser");

        if (idCategorie == null || idCategorie.isEmpty()
        		|| titre == null || titre.isEmpty()
        		|| contenu == null || contenu.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        int idSubject = 0;
        
        try (Connection conn = DBConnection.getConnection()) {
            // Récupérer la catégorie
            String queryInsertSubject = "INSERT INTO sujets (titre_sujet, contenu_sujet, id_categorie, id_utilisateur) VALUES (?, ?, ?, ?);";
            PreparedStatement stmtInsertSubject = conn.prepareStatement(queryInsertSubject);
            stmtInsertSubject.setString(1, titre);
            stmtInsertSubject.setString(2, contenu);
            stmtInsertSubject.setInt(3, Integer.parseInt(idCategorie));
            stmtInsertSubject.setInt(1, Integer.parseInt(idUser));
            
            int rowsAffected = stmtInsertSubject.executeUpdate();
            
            if(rowsAffected > 0) {
            	//Vérification
                String querySujet = "SELECT id_sujet FROM sujets"
                		+ " WHERE titre_sujet = ?"
                		+ " AND contenu_sujet = ?"
                		+ " AND id_categorie = ?"
                		+ " AND id_utilisateur = ?";

                PreparedStatement stmtSujet = conn.prepareStatement(querySujet);
                stmtSujet.setString(1, titre);
                stmtSujet.setString(2, contenu);
                stmtSujet.setInt(3, Integer.parseInt(idCategorie));
                stmtSujet.setInt(4, Integer.parseInt(idUser));
                
                ResultSet rsSujets = stmtSujet.executeQuery();

                while (rsSujets.next()) {
                    idSubject = rsSujets.getInt("id_sujet");
                }
            }
            request.setAttribute("idSubject", idSubject);
            RequestDispatcher dispatcher = request.getRequestDispatcher("sujet.jsp");
            dispatcher.forward(request, response);
            
        }catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur SQL
            System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

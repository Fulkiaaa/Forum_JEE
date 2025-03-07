package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Category;
import models.Subject;
import models.User;
import utils.DBConnection;

@SuppressWarnings("serial")
public class CreationSubjectServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		Connection conn = null;
    	PreparedStatement stmtInsertSubject = null;
    	PreparedStatement stmtSujet = null;
    	ResultSet rsSujets = null;
    	
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

        String sujetId = null;
        try {
        	conn = DBConnection.getConnection();
            String queryInsertSubject = "INSERT INTO sujets (titre_sujet, contenu_sujet, id_categorie, id_utilisateur) VALUES (?, ?, ?, ?);";
      
        	stmtInsertSubject = conn.prepareStatement(queryInsertSubject);
            stmtInsertSubject.setString(1, titre);
            stmtInsertSubject.setString(2, contenu);
            stmtInsertSubject.setInt(3, Integer.parseInt(idCategorie));
            stmtInsertSubject.setInt(4, Integer.parseInt(idUser));
                
            int rowsAffected = stmtInsertSubject.executeUpdate();

            if (rowsAffected > 0) {
                String querySujet = "SELECT id_sujet FROM sujets WHERE titre_sujet = ? AND contenu_sujet = ? AND id_categorie = ? AND id_utilisateur = ?";
                   	stmtSujet = conn.prepareStatement(querySujet);
                    stmtSujet.setString(1, titre);
                    stmtSujet.setString(2, contenu);
                    stmtSujet.setInt(3, Integer.parseInt(idCategorie));
                    stmtSujet.setInt(4, Integer.parseInt(idUser));
                    
                    rsSujets = stmtSujet.executeQuery();
                    	
                        if (rsSujets.next()) {                            	
                        	sujetId = rsSujets.getString("id_sujet");
                        }
                    
                }                
            response.sendRedirect("sujet?id=" + sujetId);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }finally {
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            try { if (rsSujets != null) rsSujets.close(); } catch (SQLException ignored) {}
            try { if (stmtSujet != null) stmtSujet.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}
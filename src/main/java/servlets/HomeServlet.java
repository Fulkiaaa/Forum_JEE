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
import models.User;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	Connection conn = null;
    	PreparedStatement stmtSujets = null;
    	PreparedStatement stmtCategorie = null;
    	ResultSet rsSujets = null;
    	ResultSet rsCategorie = null;
    	
        ArrayList<Subject> sujets = new ArrayList<>();
        Category categorie = null;

        try {
        	conn = DBConnection.getConnection();
            // Récupérer la catégorie
            String queryCategorie = "SELECT id_categorie, nom_categorie FROM categories";
            stmtCategorie = conn.prepareStatement(queryCategorie);
            rsCategorie = stmtCategorie.executeQuery();
            
            if (rsCategorie.next()) {
                categorie = new Category(rsCategorie.getInt("id_categorie"), rsCategorie.getString("nom_categorie"));
            }

            // Récupérer les sujets de cette catégorie avec l'utilisateur associé
            String querySujets = "SELECT s.id_sujet, s.titre_sujet, s.contenu_sujet, s.date_creation,"
            		+ " u.id_utilisateur, u.nom_utilisateur, u.email, u.mot_de_passe"
            		+ " FROM sujets s"
            		+ " INNER JOIN utilisateurs u ON s.id_utilisateur = u.id_utilisateur" +
            		" ORDER BY date_creation DESC LIMIT 5";

            stmtSujets = conn.prepareStatement(querySujets);
            rsSujets = stmtSujets.executeQuery();

            while (rsSujets.next()) {
                User utilisateur = new User(
                    rsSujets.getInt("id_utilisateur"),
                    rsSujets.getString("nom_utilisateur"),
                    rsSujets.getString("email"),
                    rsSujets.getString("mot_de_passe")
                );
                
                Subject sujet = new Subject(
                    rsSujets.getInt("id_sujet"),
                    rsSujets.getString("titre_sujet"),
                    rsSujets.getString("contenu_sujet"),
                    rsSujets.getDate("date_creation"),
                    categorie, utilisateur
                );

                sujets.add(sujet);
            }

            request.setAttribute("subjects", sujets);
            request.setAttribute("category", categorie);
            // Forward vers index.jsp
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }finally { 
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            try { if (rsCategorie != null) rsCategorie.close(); } catch (SQLException ignored) {}
            try { if (rsSujets != null) rsSujets.close(); } catch (SQLException ignored) {}
            try { if (stmtSujets != null) stmtSujets.close(); } catch (SQLException ignored) {}
            try { if (stmtCategorie != null) stmtCategorie.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}

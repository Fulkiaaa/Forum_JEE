package filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import models.Category;
import utils.DBConnection;

public class HeaderFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	Connection conn = null;
    	PreparedStatement stmtCategorie = null;
    	ResultSet rsCategorie = null;
    	
        // Logique de filtre, ici on pourrait ajouter des données à la requête
        try {
        	conn = DBConnection.getConnection();
            ArrayList<Category> categories = new ArrayList<>();
            String queryCategorie = "SELECT id_categorie, nom_categorie FROM categories";
            stmtCategorie = conn.prepareStatement(queryCategorie);
            rsCategorie = stmtCategorie.executeQuery();
            
            while (rsCategorie.next()) {
                Category categorie = new Category(rsCategorie.getInt("id_categorie"), rsCategorie.getString("nom_categorie"));
                categories.add(categorie);
            }

            // Ajouter les catégories à la requête avant de continuer le traitement
            request.setAttribute("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // Fermeture des ressources dans l'ordre inverse de leur ouverture
            try { if (rsCategorie != null) rsCategorie.close(); } catch (SQLException ignored) {}
            try { if (stmtCategorie != null) stmtCategorie.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }	

        // Continuer avec la chaîne de filtres ou le servlet
        chain.doFilter(request, response);
    }
}

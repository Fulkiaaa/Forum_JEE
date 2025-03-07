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
public class CreationCategoryServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {     

		HttpSession session = request.getSession();
        
		User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("connection.jsp");
            return;
        }
        
        if(!user.getRole().getName().equals("administrateur")) {
        	response.sendRedirect("connection.jsp");
            return;
        }
	        
	    String idUser = Integer.toString(user.getId());
	        
        String nameCategory = request.getParameter("title");
        
        if (nameCategory == null || nameCategory.isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        String categoryId = null;
        try (Connection conn = DBConnection.getConnection()) {
            String queryInsertSubject = "INSERT INTO categories (nom_categorie) VALUES (?);";
            try (PreparedStatement stmtInsertSubject = conn.prepareStatement(queryInsertSubject)) {
                stmtInsertSubject.setString(1, nameCategory);
                
                int rowsAffected = stmtInsertSubject.executeUpdate();

                if (rowsAffected > 0) {
                    String querySujet = "SELECT id_categorie FROM categories WHERE nom_categorie = ?";
                    try (PreparedStatement stmtSujet = conn.prepareStatement(querySujet)) {
                        stmtSujet.setString(1, nameCategory);
                        
                        try (ResultSet rsSujets = stmtSujet.executeQuery()) {
                        	
                            if (rsSujets.next()) {                            	
                            	categoryId = rsSujets.getString("id_categorie");
                            }
                        }
                    }
                }
            }
            
            // Redirection vers la page précédente
            response.sendRedirect("category?id=" + categoryId);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
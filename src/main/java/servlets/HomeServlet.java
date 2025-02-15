package servlets;

import models.Subject;
import utils.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Subject> recentSubjects = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
        
        	String query = "SELECT * FROM sujets ORDER BY date_creation DESC LIMIT 5";
            
        	PreparedStatement stmt = conn.prepareStatement(query);
            
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
            	Subject subject = new Subject(
            
                		rs.getInt("id_sujet"),
                        rs.getString("titre_sujet"),
                        rs.getString("contenu_sujet"),
                        rs.getInt("id_categorie"),
                        rs.getInt("id_utilisateur"),
                        rs.getTimestamp("date_creation")
                );
                
            	recentSubjects.add(subject);
                
            	System.out.println("Sujet trouvé : " + subject.getTitle());
            }
        } 
        
        catch (Exception e) {
        
        	e.printStackTrace();
        }

        System.out.println("Nombre de sujets récupérés : " + recentSubjects.size());
        
        request.setAttribute("recentSubjects", recentSubjects);
        
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

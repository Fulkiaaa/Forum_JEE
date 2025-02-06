package servlets;

import utils.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreationMessageServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String idSujetStr = request.getParameter("id_sujet");
	        if (idSujetStr == null || idSujetStr.isEmpty()) {
	            request.setAttribute("errorMessage", "L'ID du sujet est manquant.");
	            request.getRequestDispatcher("creation_message.jsp").forward(request, response);
	            return;
	        }

	        int idSujet = Integer.parseInt(idSujetStr);
	        String contenuMessage = request.getParameter("contenu_message");

	        if (contenuMessage == null || contenuMessage.trim().isEmpty()) {
	            request.setAttribute("errorMessage", "Le message ne peut pas être vide !");
	            request.getRequestDispatcher("creation_message.jsp").forward(request, response);
	            return;
	        }

	        Connection conn = DBConnection.getConnection();
	        String query = "INSERT INTO messages (contenu_message, id_sujet, id_utilisateur, date_creation) VALUES (?, ?, ?, NOW())";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, contenuMessage);
	        stmt.setInt(2, idSujet);
	        stmt.setInt(3, 1); // Remplace 1 par l'ID de l'utilisateur connecté

	        stmt.executeUpdate();
	        conn.close();

	        response.sendRedirect("sujet.jsp?id=" + idSujet);
	    } catch (NumberFormatException e) {
	        request.setAttribute("errorMessage", "Format d'ID sujet invalide.");
	        request.getRequestDispatcher("creation_message.jsp").forward(request, response);
	    } catch (Exception e) {
	        request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
	        request.getRequestDispatcher("creation_message.jsp").forward(request, response);
	    }
	}
}

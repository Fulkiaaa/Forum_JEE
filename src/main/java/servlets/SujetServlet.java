package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.DBConnection;

public class SujetServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idSujet = request.getParameter("id");

        if (idSujet == null || idSujet.isEmpty()) {
            response.sendRedirect("accueil.jsp");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            
            // Récupération du titre du sujet
            String querySujet = "SELECT titre_sujet FROM sujets WHERE id_sujet = ?";
            PreparedStatement stmtSujet = conn.prepareStatement(querySujet);
            stmtSujet.setInt(1, Integer.parseInt(idSujet));
            ResultSet rsSujet = stmtSujet.executeQuery();
            
            String titreSujet = "Sujet inconnu";
            if (rsSujet.next()) {
                titreSujet = rsSujet.getString("titre_sujet");
            }

            // Récupération des messages du sujet
            String queryMessages = "SELECT m.contenu_message, u.nom_utilisateur, m.date_creation FROM messages m " +
                                   "JOIN utilisateurs u ON m.id_utilisateur = u.id_utilisateur " +
                                   "WHERE m.id_sujet = ? ORDER BY m.date_creation ASC";
            PreparedStatement stmtMessages = conn.prepareStatement(queryMessages);
            stmtMessages.setInt(1, Integer.parseInt(idSujet));
            ResultSet rsMessages = stmtMessages.executeQuery();

            List<HashMap<String, String>> messages = new ArrayList<>();
            while (rsMessages.next()) {
                HashMap<String, String> message = new HashMap<>();
                message.put("auteur", rsMessages.getString("nom_utilisateur"));
                message.put("contenu", rsMessages.getString("contenu_message"));
                message.put("date", rsMessages.getString("date_creation"));
                messages.add(message);
            }

            // Envoi des données à la JSP
            request.setAttribute("titreSujet", titreSujet);
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("sujet.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur serveur : " + e.getMessage());
            request.getRequestDispatcher("accueil.jsp").forward(request, response);
        }
    }
}

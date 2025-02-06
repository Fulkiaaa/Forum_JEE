package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import utils.DBConnection;
import models.Category;
import models.Subject;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idCategorie = request.getParameter("id");

        if (idCategorie == null || idCategorie.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        ArrayList<Subject> sujetsLst = new ArrayList<>();
        String nomCategorie = "";

        try (Connection conn = DBConnection.getConnection()) {
            // Récupérer le nom de la catégorie
            String queryCategorie = "SELECT nom_categorie FROM categories WHERE id_categorie = ?";
            PreparedStatement stmtCategorie = conn.prepareStatement(queryCategorie);
            stmtCategorie.setInt(1, Integer.parseInt(idCategorie));
            ResultSet rsCategorie = stmtCategorie.executeQuery();
            
            if (rsCategorie.next()) {
                nomCategorie = rsCategorie.getString("nom_categorie");
            }
            
            Category cat = new Category(Integer.parseInt(idCategorie), nomCategorie);

            // Récupérer les sujets de cette catégorie
            String querySujets = "SELECT id_sujet, titre_sujet, contenu_sujet, date_creation FROM sujets WHERE id_categorie = ?";
            PreparedStatement stmtSujets = conn.prepareStatement(querySujets);
            stmtSujets.setInt(1, Integer.parseInt(idCategorie));
            ResultSet rsSujets = stmtSujets.executeQuery();

            while (rsSujets.next()) {
            	Subject sujet = new Subject(rsSujets.getInt("id_sujet"), 
            			rsSujets.getString("titre_sujet"), rsSujets.getString("contenu_sujet"), 
            			rsSujets.getDate("date_creation"), cat);
            	
            	sujetsLst.add(sujet);
            }

            request.setAttribute("sujets", sujetsLst);
            request.setAttribute("nomCategorie", nomCategorie);
            RequestDispatcher dispatcher = request.getRequestDispatcher("categorie.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

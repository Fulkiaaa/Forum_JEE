package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jakarta.servlet.*;

public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idCategorie = request.getParameter("id");

        if (idCategorie == null || idCategorie.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        ArrayList<Subject> sujets = new ArrayList<>();
        Category categorie = null;

        try (Connection conn = DBConnection.getConnection()) {
            // Récupérer la catégorie
            String queryCategorie = "SELECT id_categorie, nom_categorie FROM categories WHERE id_categorie = ?";
            PreparedStatement stmtCategorie = conn.prepareStatement(queryCategorie);
            stmtCategorie.setInt(1, Integer.parseInt(idCategorie));
            ResultSet rsCategorie = stmtCategorie.executeQuery();
            
            if (rsCategorie.next()) {
                categorie = new Category(rsCategorie.getInt("id_categorie"), rsCategorie.getString("nom_categorie"));
            }

            // Récupérer les sujets de cette catégorie avec l'utilisateur associé
            String querySujets = "SELECT s.id_sujet, s.titre_sujet, s.contenu_sujet, s.date_creation,"
            		+ " u.id_utilisateur, u.nom_utilisateur, u.email, u.mot_de_passe"
            		+ " FROM sujets s"
            		+ " JOIN utilisateurs u ON s.id_utilisateur = u.id_utilisateur"
            		+ " WHERE s.id_categorie = ?";

            PreparedStatement stmtSujets = conn.prepareStatement(querySujets);
            stmtSujets.setInt(1, Integer.parseInt(idCategorie));
            ResultSet rsSujets = stmtSujets.executeQuery();

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

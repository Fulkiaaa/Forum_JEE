<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.HashMap" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("titreSujet") %></title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="container">
        <h2 class="text-center my-4"><%= request.getAttribute("titreSujet") %></h2>

        <% 
            List<HashMap<String, String>> messages = (List<HashMap<String, String>>) request.getAttribute("messages");
            if (messages != null && !messages.isEmpty()) {
                for (HashMap<String, String> message : messages) { 
        %>
                    <div class="card mb-3">
                        <div class="card-header">
                            <strong><%= message.get("auteur") %></strong> - <small><%= message.get("date") %></small>
                        </div>
                        <div class="card-body">
                            <p><%= message.get("contenu") %></p>
                        </div>
                    </div>
        <% 
                }
            } else { 
        %>
                <div class="alert alert-warning text-center">Aucun message dans ce sujet.</div>
        <% } %>

        <!-- Formulaire pour ajouter un message -->
        <form action="ajoutMessage" method="post">
            <input type="hidden" name="id_sujet" value="<%= request.getParameter("id") %>">
            <div class="mb-3">
                <label for="contenu" class="form-label">Votre message :</label>
                <textarea class="form-control" id="contenu" name="contenu" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Envoyer</button>
        </form>
        
        <div class="text-center mt-3">
            <a href="home.jsp" class="btn btn-secondary">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>

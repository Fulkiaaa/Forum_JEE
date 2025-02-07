<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer un message</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-lg">
                    <div class="card-header text-center">
                        <h3>Ajouter un message</h3>
                    </div>
                    <div class="card-body">
                        <% 
                            HttpSession sessionUser = request.getSession();
                            if (sessionUser.getAttribute("userId") == null) { 
                        %>
                            <div class="alert alert-danger">Vous devez être connecté pour poster un message.</div>
                        <% } else { %>
                            <form action="ajoutMessage" method="post">
                                <input type="hidden" name="id_sujet" value="<%= request.getParameter("id") %>">
                                <div class="mb-3">
                                    <label for="contenu_message" class="form-label">Votre message</label>
                                    <textarea class="form-control" id="contenu_message" name="contenu_message" rows="4" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Envoyer</button>
                            </form>
                        <% } %>
                    </div>
                    <div class="card-footer text-center">
                        <a href="sujet.jsp?id=<%= request.getParameter("id_sujet") %>">Retour au sujet</a>
                    </div>
                </div>
                
                <%-- Affichage des messages d'erreur --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger mt-3"><%= request.getAttribute("errorMessage") %></div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>

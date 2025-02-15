<%@ include file="header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="models.Subject, models.User, models.Category" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Sujet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>

<main class="container my-5">
    <%
    Subject sujet = (Subject) request.getAttribute("subject");
    if (sujet != null) {
        User auteur = sujet.getUser();
        Category categorie = sujet.getCategory();
    %>
        <h1 class="display-4"><%= sujet.getTitle() %></h1>
        <p class="text-muted">Posté par <strong><%= auteur.getUsername() %></strong> le <span class="date"><%= sujet.getDateFormated() %></span></p>
        <p><%= sujet.getContent() %></p>

        <hr>
        <p><a href="category?id=<%= categorie.getId() %>" class="btn btn-outline-primary">Retour à la catégorie : <%= categorie.getName() %></a></p>
    <%
    } else {
    %>
        <p class="text-danger">Aucun sujet trouvé.</p>
    <%
    }
    %>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>

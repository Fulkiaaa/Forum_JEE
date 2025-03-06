<%@ include file="header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Subject, models.User, models.Category, models.Message" %>

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
    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/category.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    
</head>
<body>

<main class="container my-5">
    <%
    Subject sujet = (Subject) request.getAttribute("sujet");
    List<Message> messages = (List<Message>) request.getAttribute("messages");
   
    boolean isLoggedIn = (currentUser != null);
    
    if (sujet != null) {
        User auteur = sujet.getUser();
        Category categorie = sujet.getCategory();
    %>
        <p><a href="category?id=<%= categorie.getId() %>" class="btn btn-outline-primary">Retour à la catégorie : <%= categorie.getName() %></a></p>
    
        <h1 class="display-4"><%= sujet.getTitle() %></h1>
        <p class="text-muted">Posté par <strong><%= auteur.getUsername() %></strong> le <span class="date"><%= sujet.getDateFormated() %></span></p>
        <p><%= sujet.getContent() %></p>

        <hr>
        <h2>Messages</h2>
        <div class="messages">
            <%
            if (messages != null && !messages.isEmpty()) {
                for (Message message : messages) {
                    User auteurMessage = message.getUser();
            %>
                    <div class="message border p-3 my-2">
                        <p><strong><%= auteurMessage.getUsername() %></strong> a écrit :</p>
                        <p><%= message.getContenu() %></p>
                        <p class="text-muted"><small>Posté le <%= message.getDateFormated() %></small></p>
                    </div>
            <%
                }
            } else {
            %>
                <p>Aucun message pour ce sujet.</p>
            <%
            }
            %>
        </div>
		<a href="#" class="floating-button" data-bs-toggle="modal" data-bs-target="#createMessageModal">
		    <i class="fas fa-plus"></i>
		</a>  
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
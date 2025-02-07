<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Sujets</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/category.css">
    <%@ page contentType="text/html; charset=UTF-8" %>
	<%@ page import="java.util.List" %>
    <%@ page import="models.Subject, models.Category, models.User" %>
    


    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SportsZone</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
    <main class="container my-5 text-center">
        
        <%        
        Category categorie = (Category) request.getAttribute("category");
        if (categorie != null) {
    	%>
        	<h1 class="display-4">Catégorie : <%= categorie.getName() %></h1>
    	<%
        }
    	%>

  
        <%
        List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
        if (subjects != null && !subjects.isEmpty()) {
        	for (Subject subject : subjects) {
            	User auteur = subject.getUser();            	
        %>
        <%-- <li>
            <a href="SujetServlet?id=<%= subject.getId() %>"><%= subject.getTitle() %></a>
            <small>par <%= auteur.getUsername() %></small>
        </li> --%>
        
        <div class="entry">
	        <h2><a href="#"><%= subject.getTitle() %></a></h2>
	        <p><%= subject.getContent() %></p>
	        <p class="meta">Posté par <%= auteur.getUsername() %> le <span class="date"><%= subject.getDateFormated() %></span></p>
	    </div>
        <%
            }
        } else {
        %>
        	<p>Aucun sujet trouvé.</p>
        <%
        }
        %>
        
		<a href="#" class="floating-button" data-bs-toggle="modal" data-bs-target="#createSubjectModal">
		    <i class="fas fa-plus"></i>
		</a>    
		<%
    /* User currentUser = (User) session.getAttribute("user");
    boolean isLoggedIn = (currentUser != null); */
    boolean isLoggedIn = true;
%>
		
		
		<!-- Modal -->
	<div class="modal fade" id="createSubjectModal" tabindex="-1" aria-labelledby="createSubjectModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title text-center w-100" id="createSubjectModalLabel">Créer un nouveau sujet</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
	            </div>
	            <div class="modal-body">
	                <% if (isLoggedIn) { %>
	                    <!-- Formulaire d'ajout de sujet -->
	                    <form action="SujetServlet" method="POST">
	                        <div class="mb-3">
	                            <label for="title" class="form-label">Titre du sujet</label>
	                            <input type="text" class="form-control" id="title" name="title" required>
	                        </div>
	                        <div class="mb-3">
	                            <label for="content" class="form-label">Contenu</label>
	                            <textarea class="form-control" id="content" name="content" rows="3" required></textarea>
	                        </div>
	                        <button type="submit" class="btn btn-primary">Créer</button>
	                    </form>
	                <% } else { %>
	                    <!-- Message d'erreur si l'utilisateur n'est pas connecté -->
	                    <div class="alert alert-warning" role="alert">
	                        Vous devez être connecté pour créer un sujet. <a href="connection.jsp" class="alert-link">Connectez-vous ici.</a>
	                    </div>
	                <% } %>
	            </div>
	        </div>
	    </div>
	</div>
		
				

    <a href="index.jsp">Retour à l'accueil</a>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
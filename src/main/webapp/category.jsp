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

    <a href="index.jsp">Retour</a>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
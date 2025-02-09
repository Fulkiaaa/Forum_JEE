<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Subject" %>

<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SportsZone</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/style.css">
</head>

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
        <h1 class="display-4">Welcome to SportsZone</h1>
    </main>
    
    <div class="container mt-5">
        <div id="subjectsCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <%
                    // Récupérer l'attribut "recentSubjects" passé par le servlet
                    @SuppressWarnings("unchecked")
                    List<Subject> recentSubjects = (List<Subject>) request.getAttribute("recentSubjects");

                    // Vérifier si recentSubjects n'est pas vide
                    if (recentSubjects != null && !recentSubjects.isEmpty()) {
                        // Boucle pour afficher chaque sujet dans le carousel
                        for (int i = 0; i < recentSubjects.size(); i++) {
                            Subject subject = recentSubjects.get(i);
                %>
                    <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
                        <div class="d-block w-100 p-5 bg-light">
                            <h3><%= subject.getTitle() %></h3>
                            <p><%= subject.getContent() %></p>
                            <small>Posté le <%= subject.getCreationDate() %></small>
                        </div>
                    </div>
                <% 
                        }
                    } else {
                %>
                    <div class="carousel-item active">
                        <div class="d-block w-100 p-5 bg-light">
                            <p>Aucun sujet récent disponible.</p>
                        </div>
                    </div>
                <% 
                    }
                %>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#subjectsCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Précédent</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#subjectsCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Suivant</span>
            </button>
        </div>
    </div>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
    
    <%@ include file="footer.jsp" %>
</body>
</html>
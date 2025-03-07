<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Subject" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SportsZone</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <main class="container my-5 text-center">
        <h1 class="display-4">Welcome to SportsZone</h1>

        <div class="row mt-5">
            <div class="col-md-12">
                <h2 class="text-center mb-4">Derniers sujets</h2>
                
                <% List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
                if (subjects != null && !subjects.isEmpty()) { %>
                    <div id="subjectCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <% for (int i = 0; i < subjects.size(); i++) { 
                                Subject subject = subjects.get(i);
                                User auteur = subject.getUser(); %>
                                <div class="carousel-item <%= i == 0 ? "active" : "" %>">
                                    <div class="entry">
                                        <h2><a href="sujet?id=<%= subject.getId() %>"><%= subject.getTitle() %></a></h2>
                                        <p><%= subject.getContent() %></p>
                                        <p class="meta">Posté par <%= auteur.getUsername() %> le <span class="date"><%= subject.getDateFormated() %></span></p>
                                    </div>
                                </div>
                            <% } %>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#subjectCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#subjectCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                <% } else { %>
                    <p>Aucun sujet trouvé.</p>
                <% } %>
            </div>
        </div>
    </main>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
    <%@ include file="footer.jsp" %>
</body>
</html>
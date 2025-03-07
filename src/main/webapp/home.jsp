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
                <h2 class="text-center mb-4">Derniers sujets.</h2>
                <div class="list-group">
                    <% 
                    	@SuppressWarnings("unchecked")
                        List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
                        if (subjects != null && !subjects.isEmpty()) {
                            for (Subject subject : subjects) {
                    %>
                    <a href="sujetServlet?id=<%= subject.getId() %>" class="list-group-item list-group-item-action">
                        <h5><%= subject.getTitle() %></h5>
                        <p><%= subject.getContent() %></p>
                        <p class="text-muted">Category: <%= subject.getCategory().getName() %> | Posted by: <%= subject.getUser().getUsername() %> | On: <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(subject.getDate()) %></p>
                    </a>
                    <% 
                            }
                        } else {
                    %>
                    <p>Aucun sujet trouvé.</p>
                    <% 
                        }
                    %>
                </div>
            </div>
        </div>
    </main>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
    <%@ include file="footer.jsp" %>
</body>
</html>

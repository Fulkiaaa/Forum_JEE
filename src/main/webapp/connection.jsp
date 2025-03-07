<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card shadow-lg">
                    <div class="card-header text-center">
                    <a href="home.jsp" class="position-absolute start-0 ms-3 text-dark">
    					<i class="fas fa-arrow-left fa-2x"></i>
					</a>
                        <h3>Connexion</h3>
                    </div>
                    <div class="card-body">
                        <form action="connection" method="post">
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Mot de passe</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <p>Pas encore de compte ? <a href="register.jsp">Inscrivez-vous</a></p>
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
